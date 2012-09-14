package jerry.c2c.service.tests;

import java.util.List;

import jerry.c2c.domain.Category;
import jerry.c2c.exception.BusinessException;
import jerry.c2c.service.CategoryService;


public class TestCategoryService extends SpringTestSupport
{
	
	private CategoryService categoryService = null;
	/* (non-Javadoc)
	 * @see jerry.c2c.service.tests.SpringTestSupport#setUp()
	 */
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		categoryService = (CategoryService)context.getBean("categoryService");
	}

	public void testFindByKeyword()
	{
		Category c1 = new Category();
		Category c2 = new Category();
		Category c3 = new Category();
		Category c4 = new Category();
		c1.setName("C++");
		c1.setDescription("C++ is the king of OO languages");
		c2.setName("Java");
		c2.setDescription("Derived from C++");
		c3.setName("C++ in Action");
		c3.setDescription("How to user this language in action");
		c4.setName("Ruby");
		c4.setDescription("A dynamic language");
	
		try
		{
			categoryService.save(c1);
			categoryService.save(c2);
			categoryService.save(c3);
			categoryService.save(c4);
			List result = categoryService.findByKeyword("C++");
			assertTrue(result.contains(c1));
			assertTrue(result.contains(c2));
			assertTrue(result.contains(c3));
			assertTrue(result.size()==3);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

	public void testFindByParent()
	{
		Category c1 = new Category();
		Category c2 = new Category();
		Category c3 = new Category();
		Category c4 = new Category();
		c1.setName("C++");
		c2.setName("VC++ and MFC");
		c3.setName("C++ game development");
		c4.setName("C++ RPG development");
		
		c2.setParent(c1);
		c3.setParent(c1);
		c4.setParent(c3);
	
		try
		{
			categoryService.save(c1);
			categoryService.save(c2);
			categoryService.save(c3);
			categoryService.save(c4);
			List childs = categoryService.findByParent(c1);
			List childs2 = categoryService.findByParent(c3);
			assertTrue(childs.contains(c2));
			assertTrue(childs.contains(c3));
			assertTrue(childs.size()==2);
			assertTrue(childs2.contains(c4));
			assertTrue(childs2.size()==1);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

	public void testFindRoots()
	{
		Category c1 = new Category();
		Category c2 = new Category();
		Category c3 = new Category();
		c1.setName("C++");
		c2.setName("Java");
		c3.setName("J2EE");
		c3.setParent(c2);
	
		try
		{
			categoryService.save(c1);
			categoryService.save(c2);
			categoryService.save(c3);
			List roots = categoryService.findRoots();
			assertTrue(roots.contains(c1));
			assertTrue(roots.contains(c2));
			assertFalse(roots.contains(c3));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

	public void testSaveGetUpdate()
	{
		Category c1 = new Category();
		Category c2 = new Category();
		c1.setName("Computer Programming");
		c2.setName("Java");
		c2.setParent(c1);
		
		try
		{
			categoryService.save(c1);
			categoryService.save(c2);
			Category c_2 = categoryService.getById(c2.getId());
			assertEquals(c_2,c2);
			assertEquals(c_2.getParent(),c1);
			c2.setParent(null);
			c2.setName("游戏开发");
			categoryService.update(c2);
			c_2 = categoryService.getById(c2.getId());
			assertEquals(c_2.getName(),"游戏开发");
			assertNull(c_2.getParent());
		}
		catch (BusinessException e)
		{
			e.printStackTrace();
			fail("Exception:" + e.getMessage());
		}
	}

}
