package jerry.c2c.service.tests;

import java.util.List;

import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.UserService;


public class TestUserService extends SpringTestSupport
{
	private UserService userService = null;
	
	

	/* (non-Javadoc)
	 * @see jerry.c2c.service.tests.SpringTestSupport#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		userService = (UserService)context.getBean("userService");
	}

	public void testFindByKeyword()
	{
		User user = new User();
		user.setName("彭睿");
		user.setPassword("123");
		
		User user2 = new User();
		user2.setName("徐睿");
		user2.setPassword("1234");
		try
		{
			userService.save(user);
			userService.save(user2);
			List temp = userService.findByKeyword("睿");
			assertTrue(temp.contains(user));
			assertTrue(temp.contains(user2));
			
		}
		catch(BusinessException e)
		{
			fail("Exception:" + e);
		}
	}

	public void testGetByName()
	{
		User user = new User();
		user.setName("Jerry");
		user.setPassword("123");
		try
		{
			userService.save(user);
			User user2 = userService.getByName("Jerry");
			User user3 = userService.getByName("hhaha");
			assertEquals(user2,user);
			assertNull(user3);
		}
		catch(BusinessException e)
		{
			System.out.println(e);
		}
	}

	public void testLogin()
	{
		User user = new User();
		user.setName("Jerry");
		user.setPassword("123");
		
		try
		{
			userService.save(user);
			User user2 = userService.login("Jerry", "123");
			User user3 = userService.login("Jerry", "21343");
			assertEquals(user2,user);
			assertNull(user3);
		}
		catch (BusinessException e)
		{
			System.out.println(e);
		}
	}

	public void testSaveDeleteUpdate()
	{
		User user = new User();
		user.setName("Jerry");
		user.setPassword("123");
		try
		{
			userService.save(user);
			User user2 = userService.getById(user.getId());
			assertEquals(user2,user);
			user.setName("Peng");
			userService.update(user);
			user2 = userService.getById(user.getId());
			assertEquals(user2.getName(),user.getName());
			userService.delete(user);
			user2 = userService.getById(user.getId());
			assertNull(user2);
		}
		catch(BusinessException e)
		{
			System.out.println(e);
		}
	}

}
