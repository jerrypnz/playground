package jerry.c2c.service.tests;

import java.util.Set;
import jerry.c2c.domain.*;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.*;
import jerry.c2c.util.DateTimeUtil;

public class TestBidService extends SpringTestSupport
{
	private ItemService itemService = null;
	private ShopService shopService = null;
	private UserService userService = null;
	private BidService bidService = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		itemService = (ItemService) context.getBean("itemService");
		shopService = (ShopService) context.getBean("shopService");
		userService = (UserService) context.getBean("userService");
		bidService = (BidService)context.getBean("bidService");
	}
	

	public void testSaveGetUpdate()
	{
		User user = new User();
		user.setName("Jerry");
		User user2 = new User();
		user2.setName("PengSi");
		Shop shop = new Shop();
		Item item = new Item();
		shop.setName("Jerry's Java Book Shop");
		shop.setOwner(user);
		item.setName("Thinking in Java");
		item.setBelongTo(shop);
		item.setBasePrice(90);
		item.setTradePrice(110);
		Bid bid = new Bid();
		bid.setItem(item);
		bid.setCurrentPrice(91);
		bid.setMaker(user2);
		bid.setDescription("要是把价格再降低点就一口价拿下");
		
		try
		{
			userService.save(user);
			userService.save(user2);
			shopService.save(shop);
			itemService.save(item);
			bidService.save(bid);
			Bid bid1 = bidService.getById(bid.getId());
			assertEquals(bid1,bid);
			assertEquals(bid1.getMaker(),user2);
			assertEquals(bid1.getItem(),item);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
		
	}
	
	public void testSaveGetTrade()
	{
		User user = new User();
		user.setName("Jerry");
		User user2 = new User();
		user2.setName("PengSi");
		Shop shop = new Shop();
		Item item = new Item();
		shop.setName("Jerry's Java Book Shop");
		shop.setOwner(user);
		item.setName("Thinking in Java");
		item.setBelongTo(shop);
		item.setBasePrice(90);
		item.setTradePrice(110);
		Bid bid = new Bid();
		bid.setItem(item);
		bid.setCurrentPrice(110);
		bid.setMaker(user2);
		
		try
		{
			userService.save(user);
			userService.save(user2);
			shopService.save(shop);
			itemService.save(item);
			bidService.save(bid);
			Item item1 = itemService.getById(item.getId());
			Trade trade = item1.getTrade();
			assertNotNull(trade);
			assertEquals(trade.getBuyer(),user2);
			assertEquals(trade.getItem(),item);
			
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}
	
	public void testCheckBidWinner()
	{
		User user = new User();
		user.setName("Jerry");
		User user2 = new User();
		user2.setName("PengSi");
		User user3 = new User();
		user3.setName("Manman");
		
		Shop shop = new Shop();
		shop.setName("Jerry's Java Book Shop");
		shop.setOwner(user);
		
		Item item = new Item();
		item.setName("Thinking in Java");
		item.setBelongTo(shop);
		item.setBasePrice(90);
		item.setTradePrice(110);
		item.setEndTime(DateTimeUtil.getCurrentTimestamp());
		
		Item item2 = new Item();
		item2.setName("Thinking in C++");
		item2.setBelongTo(shop);
		item2.setBasePrice(68);
		item2.setTradePrice(90);
		item2.setEndTime(DateTimeUtil.getCurrentTimestamp());
		
		Item item3 = new Item();
		item3.setName("Thinking in Patterns");
		item3.setBelongTo(shop);
		item3.setBasePrice(50);
		item3.setTradePrice(60);
		item3.setEndTime(DateTimeUtil.getCurrentTimestamp());
		
		Bid bid = new Bid();
		bid.setItem(item);
		bid.setCurrentPrice(110);
		bid.setMaker(user2);
		
		Bid bid2 = new Bid();
		bid2.setItem(item2);
		bid2.setCurrentPrice(70);
		bid2.setMaker(user2);
		
		Bid bid3 = new Bid();
		bid3.setItem(item3);
		bid3.setCurrentPrice(55);
		bid3.setMaker(user2);
		
		Bid bid4 = new Bid();
		bid4.setItem(item3);
		bid4.setCurrentPrice(57);
		bid4.setMaker(user3);
		
		try
		{
			userService.save(user);
			userService.save(user2);
			userService.save(user3);
			shopService.save(shop);
			itemService.save(item);
			itemService.save(item2);
			itemService.save(item3);
			bidService.save(bid);
			bidService.save(bid2);
			bidService.save(bid3);
			bidService.save(bid4);
			
			bidService.checkBidWinner();
			Item item_1 = itemService.getById(item.getId());
			Item item_2 = itemService.getById(item2.getId());
			Item item_3 = itemService.getById(item3.getId());
			
			assertNotNull(item_1.getTrade());
			assertNotNull(item_2.getTrade());
			assertNotNull(item_3.getTrade());
			assertEquals(item_1.getTrade().getPrice(),bid.getCurrentPrice());
			assertEquals(item_2.getTrade().getPrice(),bid2.getCurrentPrice());
			assertEquals(item_3.getTrade().getPrice(),bid4.getCurrentPrice());
			
			Shop shop1 = shopService.getById(shop.getId());
			Set items = shop1.getItems();
			assertFalse(items.contains(item));
			assertFalse(items.contains(item2));
			assertFalse(items.contains(item3));
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:"+e.getMessage());
		}
	}
	

}
