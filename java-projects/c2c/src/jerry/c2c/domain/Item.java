/**
 * 
 */
package jerry.c2c.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jerry
 *
 */
public class Item
{
	private long id;
	private String name;
	private String description;
	private Category category;
	private int basePrice;
	private int tradePrice;
	private List<ItemComment> comments = new ArrayList<ItemComment>();
	private Timestamp createTime;
	private Timestamp endTime;
	private int viewTimes;
	private List<Bid> bids = new ArrayList<Bid>();
	private Trade trade;
	private Shop belongTo;
	private boolean soldOut = false;
	/**
	 * @return the soldOut
	 */
	public boolean isSoldOut()
	{
		return soldOut;
	}
	/**
	 * @param soldOut the soldOut to set
	 */
	public void setSoldOut(boolean soldOut)
	{
		this.soldOut = soldOut;
	}
	/**
	 * @return the belongTo
	 */
	public Shop getBelongTo()
	{
		return belongTo;
	}
	/**
	 * @param belongTo the belongTo to set
	 */
	public void setBelongTo(Shop belongTo)
	{
		this.belongTo = belongTo;
	}
	/**
	 * @return the basePrice
	 */
	public int getBasePrice()
	{
		return basePrice;
	}
	/**
	 * @param basePrice the basePrice to set
	 */
	public void setBasePrice(int basePrice)
	{
		this.basePrice = basePrice;
	}
	/**
	 * @return the category
	 */
	public Category getCategory()
	{
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category)
	{
		this.category = category;
	}
	/**
	 * @return the comments
	 */
	public List<ItemComment> getComments()
	{
		return comments;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(List<ItemComment> comments)
	{
		this.comments = comments;
	}
	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime()
	{
		return createTime;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	/**
	 * @return the endTime
	 */
	public Timestamp getEndTime()
	{
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Timestamp endTime)
	{
		this.endTime = endTime;
	}
	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @return the bids
	 */
	public List<Bid> getBids()
	{
		return bids;
	}
	/**
	 * @param bids the bids to set
	 */
	public void setBids(List<Bid> bids)
	{
		this.bids = bids;
	}
	/**
	 * @return the trade
	 */
	public Trade getTrade()
	{
		return trade;
	}
	/**
	 * @param trade the trade to set
	 */
	public void setTrade(Trade trade)
	{
		this.trade = trade;
	}
	/**
	 * @return the tradePrice
	 */
	public int getTradePrice()
	{
		return tradePrice;
	}
	/**
	 * @param tradePrice the tradePrice to set
	 */
	public void setTradePrice(int tradePrice)
	{
		this.tradePrice = tradePrice;
	}
	/**
	 * @return the viewTimes
	 */
	public int getViewTimes()
	{
		return viewTimes;
	}
	/**
	 * @param viewTimes the viewTimes to set
	 */
	public void setViewTimes(int viewTimes)
	{
		this.viewTimes = viewTimes;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0)
	{
		return ((Item)arg0).getId()==this.id;
	}

}
