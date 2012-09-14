package jerry.c2c.service.tests;

import java.util.List;
import java.util.Set;

import jerry.c2c.domain.Category;
import jerry.c2c.domain.Shop;
import jerry.c2c.domain.ShopComment;
import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.CategoryService;
import jerry.c2c.service.ShopService;
import jerry.c2c.service.UserService;


public class TestShopService extends SpringTestSupport
{
	private ShopService shopService = null;
	private UserService userService = null;
	private CategoryService categoryService = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		shopService = (ShopService) context.getBean("shopService");
		userService = (UserService) context.getBean("userService");
		categoryService = (CategoryService) context.getBean("categoryService");
	}

	public void testFindByCategory()
	{
		Category category = new Category();
		Category category2 = new Category();
		category.setName("Jerry");
		category2.setName("MM");
		
		Shop s1 = new Shop();
		Shop s2 = new Shop();
		Shop s3 = new Shop();
		Shop s4 = new Shop();
		
		
		s1.setName("XXXXJerryXXXX");
		s1.setCategory(category);
		s2.setName("Jerry's Bread Store");
		s2.setCategory(category);
		s3.setName("MMMMMMMM");
		s3.setCategory(category);
		s4.setName("JJJJJJJJ");
		s4.setCategory(category2);
		
		try
		{
			categoryService.save(category);
			categoryService.save(category2);
			shopService.save(s1);
			shopService.save(s2);
			shopService.save(s3);
			shopService.save(s4);
			List temp = shopService.findByCategory(category);
			assertTrue(temp.contains(s1));
			assertTrue(temp.contains(s2));
			assertTrue(temp.contains(s3));
			assertTrue(temp.size()==3);
			List temp2 = shopService.findByCategory(category2);
			assertTrue(temp2.contains(s4));
			assertTrue(temp2.size()==1);			
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

	public void testFindByCredit()
	{
		Shop s1 = new Shop();
		Shop s2 = new Shop();
		Shop s3 = new Shop();
		Shop s4 = new Shop();
		
		
		s1.setName("XXXXJerryXXXX");
		s1.setCredit(99);
		s2.setName("Jerry's Bread Store");
		s2.setCredit(93);
		s3.setName("MMMMMMMM");
		s3.setCredit(98);
		s4.setName("JJJJJJJJJJJJJJJ");
		s4.setCredit(84);
		
		try
		{
			shopService.save(s1);
			shopService.save(s2);
			shopService.save(s3);
			shopService.save(s4);
			List temp = shopService.findByCredit(90, 100);
			assertTrue(temp.contains(s1));
			assertTrue(temp.contains(s2));
			assertTrue(temp.contains(s3));
			assertTrue(temp.size()==3);
			List temp2 = shopService.findByCredit(90, 80);
			assertTrue(temp2.contains(s4));
			assertTrue(temp2.size()==1);
			
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

	public void testFindByKeyword()
	{
		Shop s1 = new Shop();
		Shop s2 = new Shop();
		Shop s3 = new Shop();
		Shop s4 = new Shop();
		
		
		s1.setName("XXXXJerryXXXX");
		s1.setDescription("About Jerry");
		s2.setName("Jerry's Bread Store");
		s2.setDescription("'Delicious',he said");
		s3.setName("MMMMMMMM");
		s3.setDescription("Another shop by Jerry");
		s4.setName("JJJJJJJJJJJJJJJ");
		s4.setDescription("MMMMMMMMMMMMMMMMM");
		
		try
		{
			shopService.save(s1);
			shopService.save(s2);
			shopService.save(s3);
			shopService.save(s4);
			List temp = shopService.findByKeyword("Jerry");
			assertTrue(temp.contains(s1));
			assertTrue(temp.contains(s2));
			assertTrue(temp.contains(s3));
			assertTrue(temp.size()==3);
			
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

	public void testSaveGetUpdate()
	{
		User user = new User();
		User user2 = new User();
		Category category = new Category();
		Shop shop = new Shop();
		user.setName("Jerry");
		user2.setName("Lee");
		category.setName("Java");
		shop.setName("JStudio Java Book Shop");
		shop.setOwner(user);
		shop.setCategory(category);
		
		try
		{
			userService.save(user);
			userService.save(user2);
			categoryService.save(category);
			shopService.save(shop);
			
			Shop shop1 = shopService.getById(shop.getId());
			assertEquals(shop1,shop);
			assertEquals(shop1.getOwner(),user);
			assertEquals(shop1.getCategory(),category);
			shop.setName("Lee's C++ Book Store");
			shop.setOwner(user2);
			shopService.update(shop);
			shop1 = shopService.getById(shop.getId());
			assertEquals(shop1.getOwner(),user2);
			assertEquals(shop1.getName(),"Lee's C++ Book Store");		
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

	public void testComment()
	{
		User user = new User();
		User user2 = new User();
		Shop shop = new Shop();
		user.setName("Jerry");
		user2.setName("彭睿");
		shop.setName("JStudio Java Book Shop");
		shop.setOwner(user);
		
		ShopComment c1 = new ShopComment();
		ShopComment c2 = new ShopComment();
		c1.setContent("A Good Shop");
		c2.setContent("Perfect Shop");
		c1.setMaker(user2);
		c2.setMaker(user2);
		c1.setDestShop(shop);
		c2.setDestShop(shop);
		
		try
		{
			userService.save(user);
			userService.save(user2);
			shopService.save(shop);
			shopService.saveComment(c1);
			shopService.saveComment(c2);
			
			ShopComment c_1 = shopService.getCommentById(c1.getId());
			ShopComment c_2 = shopService.getCommentById(c2.getId());
			Shop shop1 = shopService.getById(shop.getId());
			assertEquals(c_1,c1);
			assertEquals(c_2,c2);
			assertEquals(c_1.getMaker(),user2);
			assertEquals(c_2.getMaker(),user2);
			Set comments = shop1.getComments();
			assertTrue(comments.contains(c1));
			assertTrue(comments.contains(c2));
			
			c1.setMaker(user);
			c1.setContent("Not so perfect");
			shopService.updateComment(c1);
			c_1 = shopService.getCommentById(c1.getId());
			assertEquals(c_1.getMaker(),user);
			assertEquals(c_1.getContent(),"Not so perfect");
			
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
		
	}

}
