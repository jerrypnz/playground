package jerry.c2c.domain.tests;

import java.util.Iterator;

import org.hibernate.Query;
import org.hibernate.Session;
import jerry.c2c.domain.Item;
import jerry.c2c.domain.Shop;
import jerry.c2c.domain.ShopComment;
import jerry.c2c.domain.User;
import jerry.c2c.util.*;

public class TestShop extends HbmTestSupport
{

	private String JERRY_QUERY = "from User u where u.name='Jerry'";

	private String MANMAN_QUERY = "from User u where u.name='Manman'";

	private String SHOP_QUERY = "from Shop s where s.name='E-book shop'";

	private String ITEM_QUERY = "from Item i where i.name='深入浅出Hibernate'";

	private String COMMENT_QUERY = "from ShopComment s where s.content='你这个商店不错啊'";

	public void testAddShopToUser()
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

						ShopComment comment = new ShopComment();
						comment.setMaker(user1);
						comment.setDestShop(shop);
						comment.setTime(CURRENT_TIME);
						comment.setContent("你这个商店不错啊");

						Item item = new Item();
						item.setBelongTo(shop);
						item.setName("深入浅出Hibernate");

						shop.getItems().add(item);
						shop.getComments().add(comment);
						shop.setOwner(user);
						user.getOwnedShops().add(shop);

						session.save(user1);
						session.save(user);
						session.save(shop);
						session.save(comment);
					}
				});
		
		session.flush();
		session.close();
		session = factory.openSession();

		Query userQuery = session.createQuery(JERRY_QUERY);
		Query manmanQuery = session.createQuery(MANMAN_QUERY);
		Query shopQuery = session.createQuery(SHOP_QUERY);
		Query itemQuery = session.createQuery(ITEM_QUERY);
		Query commentQuery = session.createQuery(COMMENT_QUERY);
		

		User jerry = (User) userQuery.list().get(0);
		User manman = (User) manmanQuery.list().get(0);
		Shop shop = (Shop) shopQuery.list().get(0);
		Item item = (Item) itemQuery.list().get(0);
		ShopComment comment = (ShopComment) commentQuery.list().get(0);

		assertEquals(jerry, shop.getOwner());
		assertTrue(shop.getItems().contains(item));
		assertTrue(shop.getComments().contains(comment));
		assertEquals(manman, comment.getMaker());
		assertEquals(shop, comment.getDestShop());
		assertEquals(shop, item.getBelongTo());
		assertTrue(jerry.getOwnedShops().contains(shop));

		Iterator it = jerry.getOwnedShops().iterator();
		if (it.hasNext())
		{
			Shop a_shop = (Shop) it.next();
			System.out.println(jerry.getName() + "'s shop:" + a_shop.getName());
			System.out.println(shop.getName());
			assertEquals(shop, a_shop);
			System.out.println(jerry.getOwnedShops().contains(a_shop));
		}

	}
}
