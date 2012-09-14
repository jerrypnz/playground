package jerry.c2c.service.impl;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import jerry.c2c.domain.*;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.*;
import jerry.c2c.util.DateTimeUtil;

public class BidServiceImpl extends HibernateDaoSupport implements BidService
{
	private MessageService messageService;
	private ItemService itemService;
	private String winMessageContent;
	private String winMessageTitle;
	private String ITEM_SYMBOL = "{itemName}";
	private String FIND_BY_ITEM = "from Bid where item=? order by currentPrice desc";
	private String FIND_OUT_OF_DATE_ITEM = "from Item where endTime<=?";
	

	public void checkBidWinner() throws BusinessException
	{
		List temp = this.getHibernateTemplate()
			.find(FIND_OUT_OF_DATE_ITEM,
					DateTimeUtil.getCurrentTimestamp()
					);
		System.out.println(temp.size());
		for(int i=0;i<temp.size();i++)
		{
			Item item = (Item)temp.get(i);
			System.out.println(item.getName() + ":" +item.getEndTime());
			Bid bid = getHighestByItem(item);
			if(bid!=null)
			{
				System.out.println("Making trade:"+item.getName());
				makeTrade(bid);
			}
		}

	}

	public void delete(Bid bid)
	{
		this.getHibernateTemplate().delete(bid);
	}

	public void deleteTrade(Trade trade)
	{
		this.getHibernateTemplate().delete(trade);
		
	}

	public Bid getById(long id) throws BusinessException
	{
		try
		{
			Bid temp = (Bid)this.getHibernateTemplate()
					.get(Bid.class, id);
			this.getHibernateTemplate().initialize(temp.getItem());
			this.getHibernateTemplate().initialize(temp.getMaker());
			return temp;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Bid[id:" + id +"] does not exist");
		}
	}

	public Bid getHighestByItem(Item item)
	{
		List temp = this.getHibernateTemplate()
			.find(FIND_BY_ITEM,item);
		Bid highestBid = null;
		if(temp.size()!=0)
			highestBid = (Bid)temp.get(0);
		return highestBid;
	}
	
	/**
	 * @return the itemService
	 */
	public ItemService getItemService()
	{
		return itemService;
	}

	/**
	 * @return the messageService
	 */
	public MessageService getMessageService()
	{
		return messageService;
	}

	public Trade getTradeById(long id) throws BusinessException
	{
		try
		{
			Trade temp = (Trade)this.getHibernateTemplate()
					.get(Trade.class, id);
			this.getHibernateTemplate().initialize(temp.getItem());
			this.getHibernateTemplate().initialize(temp.getBuyer());
			return temp;
		}
		catch (DataAccessException e)
		{
			throw new BusinessException("Trade[id:" + id +"] does not exist");
		}
	}

	/**
	 * @return the winMessageContent
	 */
	public String getWinMessageContent()
	{
		return winMessageContent;
	}

	/**
	 * @return the winMessageTitle
	 */
	public String getWinMessageTitle()
	{
		return winMessageTitle;
	}

	private void makeTrade(Bid bid) throws BusinessException
	{
		Trade trade = new Trade();
		trade.setBuyer(bid.getMaker());
		trade.setItem(bid.getItem());
		trade.setPrice(bid.getCurrentPrice());
		trade.setCreateTime(DateTimeUtil.getCurrentTimestamp());
		saveTrade(trade);
		Message msg = new Message();
		StringBuffer buffer = new StringBuffer(winMessageContent);
		int pos = buffer.lastIndexOf(ITEM_SYMBOL);
		buffer.replace(pos, pos+ITEM_SYMBOL.length(), bid.getItem().getName());
		msg.setContent(buffer.toString());
		msg.setTitle(winMessageTitle);
		msg.setSendTime(DateTimeUtil.getCurrentTimestamp());
		//发送者为空表示系统消息
		messageService.send(null, bid.getMaker(), msg);
		
		Item destItem = bid.getItem();
		destItem.setSoldOut(true);
		itemService.update(destItem);
	}

	public void save(Bid bid) throws BusinessException
	{
		BidType result = validateBid(bid);
		if(result == BidType.INVALID)
			throw new BusinessException("Bid invalid,price too low or out of date");
		else if(result == BidType.WINNER)
		{
			makeTrade(bid);
		}
		else if(result == BidType.VALID)
		{
			this.getHibernateTemplate().save(bid);
		}
	}

	public void saveTrade(Trade trade) throws BusinessException
	{
		User buyer = trade.getBuyer();
		Item destItem = trade.getItem();
		if(buyer == null || destItem == null)
			throw new BusinessException("Invalid trade");
		else
			this.getHibernateTemplate().save(trade);		
	}

	/**
	 * @param itemService the itemService to set
	 */
	public void setItemService(ItemService itemService)
	{
		this.itemService = itemService;
	}

	/**
	 * @param messageService the messageService to set
	 */
	public void setMessageService(MessageService messageService)
	{
		this.messageService = messageService;
	}

	/**
	 * @param winMessageContent the winMessageContent to set
	 */
	public void setWinMessageContent(String bidWinMessage)
	{
		this.winMessageContent = bidWinMessage;
	}

	/**
	 * @param winMessageTitle the winMessageTitle to set
	 */
	public void setWinMessageTitle(String winMessageTitle)
	{
		this.winMessageTitle = winMessageTitle;
	}
	
	public BidType validateBid(Bid bid)
	{
		Item destItem = bid.getItem();
		User maker = bid.getMaker();
		if(destItem==null || maker==null)
			return BidType.INVALID;
		int basePrice = destItem.getBasePrice();
		int tradePrice = destItem.getTradePrice();
		//如果竞拍出价高于或等于商品的一口价，那么这个
		//竞拍直接获胜
		if(bid.getCurrentPrice()>=tradePrice)
			return BidType.WINNER;
		//查询在次竞拍之前出价最高的竞拍，和其出价进行比较
		Bid highest = this.getHighestByItem(destItem);
		if(highest!=null && highest.getCurrentPrice()>basePrice)
			basePrice = highest.getCurrentPrice();
		//如果没有任何人竞拍，那么只要高于商家给出的底价就行了
		if(bid.getCurrentPrice()>basePrice)
			return BidType.VALID;
		else
			return BidType.INVALID;
	}
	

}


