package jerry.c2c.domain.tests;

import jerry.c2c.domain.Item;
import jerry.c2c.domain.Shop;
import jerry.c2c.domain.Trade;
import jerry.c2c.domain.User;
import jerry.c2c.util.AbstractInTransaction;
import jerry.c2c.util.HbmTransactionUtil;

import org.hibernate.Query;
import org.hibernate.Session;

public class TestTrade extends HbmTestSupport
{
	
	public void testTrade()
	{
		HbmTransactionUtil.executeInTransaction(session,
				new AbstractInTransaction()
				{
					public void execute(Session session)
					{
						User user = new User();
						user.setName("Jerry");
						user.setEmail("c_jerry@126.com");

						User user1 = new User();
						user1.setName("Manman");
						user1.setEmail("manman@msn.com.cn");

						Shop shop = new Shop();
						shop.setName("E-book shop");
						shop.setCreateTime(CURRENT_TIME);
						shop.setCredit(100);
						shop.setOwner(user);

						Item item = new Item();
						item.setBelongTo(shop);
						item.setName("深入浅出Hibernate");
						
						Item item1 = new Item();
						item1.setBelongTo(shop);
						item1.setName("Effective C++");
						
						Trade trade = new Trade(item1);
						item1.setTrade(trade);
						trade.setDescription("请快点发货");
						trade.setBuyer(user1);

						shop.getItems().add(item);
						shop.setOwner(user);
						user.getOwnedShops().add(shop);

						session.save(user1);
						session.save(user);
						session.save(shop);
						session.save(trade);
					}
				});
		Query itemQuery = session.getNamedQuery("getItemByName");
		Query tradeQuery = session.getNamedQuery("getTradeByItem");
		Query userQuery = session.getNamedQuery("getUserByName");
		
		itemQuery.setString("name", "Effective C++");
		Item effectiveCpp = (Item)itemQuery.list().get(0);
		itemQuery.setString("name", "深入浅出Hibernate");
		Item hbm = (Item)itemQuery.list().get(0);	
		tradeQuery.setEntity("item", effectiveCpp);
		Trade trade = (Trade)tradeQuery.list().get(0);
		userQuery.setString("name", "Manman");
		User user = (User)userQuery.list().get(0);
		
		assertTrue(hbm.getTrade()==null);
		assertTrue(effectiveCpp.getTrade()!=null);
		assertEquals(trade,effectiveCpp.getTrade());
		assertEquals(user,trade.getBuyer());
		
	}

}
