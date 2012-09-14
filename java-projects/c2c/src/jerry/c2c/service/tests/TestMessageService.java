package jerry.c2c.service.tests;

import java.util.List;

import jerry.c2c.domain.Message;
import jerry.c2c.domain.User;
import jerry.c2c.service.MessageService;
import jerry.c2c.service.UserService;

public class TestMessageService extends SpringTestSupport
{
	
	private UserService userService = null;
	private MessageService msgService = null;

	/* (non-Javadoc)
	 * @see jerry.c2c.service.tests.SpringTestSupport#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		userService = (UserService)context.getBean("userService");
		msgService = (MessageService)context.getBean("messageService");
	}


	public void testSaveGetUpdate()
	{
		User from = new User();
		User to  = new User();
		from.setName("Jerry");
		to.setName("彭睿");
		Message msg = new Message();
		msg.setFrom(from);
		msg.setTo(to);
		msg.setContent("你要的东西已经到货");
		
		try
		{
			userService.save(from);
			userService.save(to);
			msgService.save(msg);
			
			Message msg1 = msgService.getById(msg.getId());
			assertEquals(msg1,msg);
			assertEquals(msg1.getFrom(),from);
			assertEquals(msg1.getTo(),to);
			
			msg.setContent("到货了，到货了");
			msgService.update(msg);
			msg1 = msgService.getById(msg.getId());
			assertEquals(msg1.getContent(),"到货了，到货了");
			
			msgService.delete(msg);
			userService.delete(from);
			userService.delete(to);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail("Exception:" + e);
		}
	}

	public void testSendReceive()
	{
		User from = new User();
		User to  = new User();
		from.setName("Jerry");
		to.setName("彭睿");
		
		Message msg1 = new Message();
		msg1.setContent("你要的东西已经到货");
		
		Message msg2 = new Message();
		msg2.setContent("最近怎么样了");
		try
		{
			userService.save(from);
			userService.save(to);
			msgService.send(from, to, msg1);
			msgService.send(from, to, msg2);
			List temp1 = msgService.receive(to);
			List temp2 = msgService.receive(from);
			assertTrue(temp2.size()==0);
			assertTrue(temp1.contains(msg1));
			assertTrue(temp1.contains(msg2));
			
			msgService.delete(msg1);
			msgService.delete(msg2);
			userService.delete(from);
			userService.delete(to);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

}
