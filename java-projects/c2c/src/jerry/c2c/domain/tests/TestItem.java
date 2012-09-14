/**
 * 
 */
package jerry.c2c.domain.tests;

import java.sql.Timestamp;
import java.util.Calendar;

import org.hibernate.Query;
import org.hibernate.Session;
import jerry.c2c.domain.Item;
import jerry.c2c.domain.ItemComment;
import jerry.c2c.domain.Bid;
import jerry.c2c.domain.User;
import jerry.c2c.util.AbstractInTransaction;
import jerry.c2c.util.HbmTransactionUtil;

/**
 * @author Jerry
 * 
 */
public class TestItem extends HbmTestSupport
{
	public void testItem()
	{
		HbmTransactionUtil.executeInTransaction(session,
				new AbstractInTransaction()
				{
					public void execute(Session session)
					{
						Item newItem = new Item();
						Calendar currentCal = Calendar.getInstance();
						newItem.setBasePrice(100);
						newItem.setTradePrice(130);
						newItem.setCreateTime(new Timestamp(currentCal
								.getTimeInMillis()));
						currentCal.add(Calendar.MONTH, 2);
						newItem.setEndTime(new Timestamp(currentCal
								.getTimeInMillis()));
						newItem.setName("Effective C++");
						newItem
								.setDescription("This is a famous book writen by Scott Meryers.");
						newItem.setViewTimes(0);

						User user = new User();
						user.setName("Jerry");
						user.setEmail("c_jerry@126.com");

						Bid bid = new Bid();
						bid.setMaker(user);
						bid.setCurrentPrice(newItem.getBasePrice() + 10);
						bid.setItem(newItem);
						bid.setDescription("Please sell to me");

						ItemComment comment = new ItemComment();
						comment.setMaker(user);
						currentCal.add(Calendar.MONTH, -2);
						comment.setTime(new Timestamp(currentCal
								.getTimeInMillis()));
						comment
								.setContent("I want to buy this book,please contact me");
						comment.setDestItem(newItem);

						newItem.getComments().add(comment);
						newItem.getBids().add(bid);

						session.save(user);
						session.save(newItem);

					}

				});
		session.close();
		session = factory.openSession();
		Query userQuery = session
				.createQuery("from User u where u.name='Jerry'");
		Query itemQuery = session
				.createQuery("from Item i where i.name='Effective C++'");
		User user = (User) userQuery.list().get(0);
		Item item = (Item) itemQuery.list().get(0);
		assertEquals("Jerry", user.getName());
		assertEquals("c_jerry@126.com", user.getEmail());
		assertEquals("Effective C++", item.getName());
		assertEquals("This is a famous book writen by Scott Meryers.",
				item.getDescription());

		ItemComment comment = item.getComments().get(0);
		Bid bid = item.getBids().get(0);

		assertEquals(user, comment.getMaker());
		assertEquals(user, bid.getMaker());
		assertEquals(110, bid.getCurrentPrice());
		assertEquals("Please sell to me", bid.getDescription());
		assertEquals("I want to buy this book,please contact me", 
				comment.getContent());
	}

}
