package jerry.c2c.domain.tests;

import jerry.c2c.domain.Message;
import jerry.c2c.domain.User;
import jerry.c2c.util.AbstractInTransaction;
import jerry.c2c.util.HbmTransactionUtil;

import org.hibernate.Query;
import org.hibernate.Session;

public class TestMessage extends HbmTestSupport
{
	
	private final static String NAME = "Jerry";
	private final static String EMAIL = "jerry@126.com";
	private final static String ADDRESS = "AAA";
	private final static String PASSWORD = "123";
	private final static String SEX = "male";
	private final static String NICKNAME = "Moonranger";
	User user = new User();
	User user1 = new User();
	Message message = new Message();

	public void testUserMessage()
	{	
		HbmTransactionUtil.executeInTransaction(session, 
				new AbstractInTransaction()
		{
			public void execute(Session s)
			{			
				user.setName(NAME);
				user.setEmail(EMAIL);
				user.setAddress(ADDRESS);
				user.setNickName(NICKNAME);
				user.setSex(SEX);
				user.setPassword(PASSWORD);				
				user1.setName("LiangSir");
				user1.setEmail("liang@126.com");							
				message.setTitle("你好");
				message.setContent("Java is a perfect programming language");
				message.setFrom(user);
				message.setTo(user1);
				s.save(user);
				s.save(user1);
				s.save(message);
				s.flush();
			}		
		});
		session.close();
		session = factory.openSession();
		Query query = session.createQuery("from Message");
		Message msg = (Message)query.list().get(0);
		assertEquals(message.getId(),msg.getId());
		assertEquals("你好",msg.getTitle());
		assertEquals("Java is a perfect programming language",msg.getContent());
		assertEquals(NAME,msg.getFrom().getName());
		assertEquals("LiangSir",msg.getTo().getName());
		System.out.println("Message Retreived:");
		System.out.println("  From:" + msg.getFrom().getName());
		System.out.println("  To:" + msg.getTo().getName());
		System.out.println("  Title:" + msg.getTitle());
		System.out.println("  Content:" + msg.getContent());
		
	}
}
