package jerry.c2c.service.tests;

import java.util.List;

import jerry.c2c.domain.Category;
import jerry.c2c.domain.Item;
import jerry.c2c.domain.ItemComment;
import jerry.c2c.domain.Shop;
import jerry.c2c.domain.User;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.CategoryService;
import jerry.c2c.service.ItemService;
import jerry.c2c.service.ShopService;
import jerry.c2c.service.UserService;


public class TestItemService extends SpringTestSupport
{
	private ItemService itemService = null;
	private ShopService shopService = null;
	private UserService userService = null;
	private CategoryService categoryService = null;

	protected void setUp() throws Exception
	{
		super.setUp();
		itemService = (ItemService) context.getBean("itemService");
		shopService = (ShopService) context.getBean("shopService");
		userService = (UserService) context.getBean("userService");
		categoryService = (CategoryService) context.getBean("categoryService");
	}
	
	public void testSaveGetUpdate()
	{
		Category category = new Category();
		Category category2 = new Category();
		Shop shop = new Shop();
		Shop shop2 = new Shop();
		Item item = new Item();
		
		shop.setName("Jerry's Java Bookstore");
		shop2.setName("Jerry's C++ Bookstore");
		category.setName("Java");
		category2.setName("C++");
		item.setName("Thinking in Java");
		item.setCategory(category);
		item.setBelongTo(shop);
		
		try
		{
			categoryService.save(category);
			categoryService.save(category2);
			shopService.save(shop);
			shopService.save(shop2);
			itemService.save(item);
			
			Item item1 = itemService.getById(item.getId());
			assertEquals(item1,item);
			assertEquals(item1.getCategory(),category);
			assertEquals(item1.getBelongTo(),shop);
			item.setName("Thinking in C++");
			item.setCategory(category2);
			item.setBelongTo(shop2);
			itemService.update(item);
			item1 = itemService.getById(item.getId());
			assertEquals(item1.getCategory(),category2);
			assertEquals(item1.getBelongTo(),shop2);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}
	
	public void testComment()
	{
		Item item = new Item();
		item.setName("Bass Guitar");
		User user = new User();
		user.setName("徐徐");
		User user2 = new User();
		user2.setName("旭旭");
		ItemComment c1 = new ItemComment();
		ItemComment c2 = new ItemComment();
		c1.setMaker(user);
		c2.setMaker(user);
		c1.setContent("五月天的玛莎是个不错的贝斯手");
		c2.setContent("你这个BASS要是再减点价，我一口价拿下");
		c1.setDestItem(item);
		c2.setDestItem(item);
		
		try
		{
			userService.save(user);
			userService.save(user2);
			itemService.save(item);
			itemService.saveComment(c1);
			itemService.saveComment(c2);
			
			ItemComment c_1 = itemService.getCommentById(c1.getId());
			ItemComment c_2 = itemService.getCommentById(c2.getId());
			assertEquals(c_1,c1);
			assertEquals(c_2,c2);
			assertEquals(c_1.getMaker(),user);
			assertEquals(c_2.getMaker(),user);
			assertEquals(c_1.getDestItem(),item);
			assertEquals(c_2.getDestItem(),item);
			
			Item item1 = itemService.getById(item.getId());
			List comments = item1.getComments();
			assertTrue(comments.contains(c1));
			assertTrue(comments.contains(c2));
			
			c1.setContent("Mayday is a great band");
			c1.setMaker(user2);
			itemService.updateComment(c1);
			c_1 = itemService.getCommentById(c1.getId());
			assertEquals(c_1.getMaker(),user2);
			assertEquals(c_1.getContent(),"Mayday is a great band");
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
		
		
	}

	public void testFindByCategory()
	{
		Shop shop = new Shop();
		shop.setName("SSSSSS");
		Category category = new Category();
		category.setName("MMMMM");
		Category category2 = new Category();
		category2.setName("JJJJJ");
		Item i1 = new Item();
		Item i2 = new Item();
		Item i3 = new Item();
		i1.setName("ghghghghgh");
		i1.setCategory(category);
		i2.setName("asasasasas");
		i2.setCategory(category);
		i3.setName("nfnfnfnfnf");
		i3.setCategory(category2);
		i1.setBelongTo(shop);
		i2.setBelongTo(shop);
		i3.setBelongTo(shop);
		
		try
		{
			shopService.save(shop);
			categoryService.save(category);
			categoryService.save(category2);
			itemService.save(i1);
			itemService.save(i2);
			itemService.save(i3);
			
			List temp1 = itemService.findByCategory(category);
			List temp2 = itemService.findByCategory(category2);
			assertTrue(temp1.contains(i1));
			assertTrue(temp1.contains(i2));
			assertTrue(temp1.size()==2);
			assertTrue(temp2.contains(i3));
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
		Shop shop = new Shop();
		shop.setName("电脑游戏店");
		Category category = new Category();
		category.setName("游戏");
		Item i1 = new Item();
		Item i2 = new Item();
		Item i3 = new Item();
		i1.setName("刀剑封魔录");
		i1.setDescription("上古传说，刀剑封魔录");
		i1.setCategory(category);
		i2.setName("刀魂");
		i2.setDescription("著名的刀剑格斗游戏");
		i2.setCategory(category);
		i3.setName("仙剑奇侠传2");
		i3.setDescription("经典的延续");
		i3.setCategory(category);
		i1.setBelongTo(shop);
		i2.setBelongTo(shop);
		i3.setBelongTo(shop);
		
		try
		{
			shopService.save(shop);
			categoryService.save(category);
			itemService.save(i1);
			itemService.save(i2);
			itemService.save(i3);
			
			List temp1 = itemService.findByKeyword("刀剑");
			List temp2 = itemService.findByKeyword("仙剑");
			assertTrue(temp1.contains(i1));
			assertTrue(temp1.contains(i2));
			assertTrue(temp1.size()==2);
			assertTrue(temp2.contains(i3));
			assertTrue(temp2.size()==1);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

	public void testFindByShop()
	{
		Shop shop = new Shop();
		shop.setName("电脑游戏店");
		Shop shop2 = new Shop();
		shop2.setName("电视游戏店");
		Category category = new Category();
		category.setName("游戏");
		Item i1 = new Item();
		Item i2 = new Item();
		Item i3 = new Item();
		i1.setName("刀剑封魔录");
		i1.setBelongTo(shop);
		i1.setCategory(category);
		i2.setName("仙剑奇侠传2");
		i2.setBelongTo(shop);
		i2.setCategory(category);
		i3.setName("刀魂");
		i3.setBelongTo(shop2);
		i3.setCategory(category);
		
		try
		{
			shopService.save(shop);
			shopService.save(shop2);
			categoryService.save(category);
			itemService.save(i1);
			itemService.save(i2);
			itemService.save(i3);
			
			List temp1 = itemService.findByShop(shop);
			List temp2 = itemService.findByShop(shop2);
			assertTrue(temp1.contains(i1));
			assertTrue(temp1.contains(i2));
			assertTrue(temp1.size()==2);
			assertTrue(temp2.contains(i3));
			assertTrue(temp2.size()==1);
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

}
