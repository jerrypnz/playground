/**
 * 
 */
package jerry.c2c.domain.tests;

import org.hibernate.Query;
import org.hibernate.Session;
import jerry.c2c.domain.Category;
import jerry.c2c.util.AbstractInTransaction;
import jerry.c2c.util.HbmTransactionUtil;

/**
 * @author Jerry
 *
 */
public class TestItemCateg extends HbmTestSupport
{
	public void testCategory()
	{	
		HbmTransactionUtil.executeInTransaction(session,
				new AbstractInTransaction()
			{
				public void execute(Session session)
				{
					Category computer = new Category("Computer");
					Category java = new Category("Java");
					Category hbm = new Category("Hibernate");
					Category cpp = new Category("C++");
					cpp.setDescription("All about C++ programming language");
					java.setParent(computer);
					cpp.setParent(computer);
					hbm.setParent(java);
					session.save(computer);
					session.save(java);
					session.save(hbm);
					session.save(cpp);
				}			
			});
		session.close();
		session = factory.openSession();
		Query hbmQuery = session.createQuery("from Category c where c.name='Hibernate' ");
		Query cppQuery = session.createQuery("from Category c where c.name='C++'");
		Category hbm = (Category)hbmQuery.list().get(0);
		Category cpp = (Category)cppQuery.list().get(0);
		Category java = hbm.getParent();
		Category computer1 = java.getParent();
		Category computer2 = cpp.getParent();
		
		assertEquals("Hibernate",hbm.getName());
		assertEquals("Java",java.getName());
		assertEquals("C++",cpp.getName());
		assertEquals("Computer",computer1.getName());
		assertEquals("Computer",computer2.getName());
		assertEquals(computer1,computer2);
	}

}
