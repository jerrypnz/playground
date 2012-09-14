/**
 * 
 */
package jerry.c2c.domain.tests;

import java.sql.Timestamp;
import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import junit.framework.TestCase;

/**
 * @author Jerry
 * 
 */
public abstract class HbmTestSupport extends TestCase
{

	protected SessionFactory factory = null;

	protected Session session = null;

	protected Calendar calendar = null;

	protected Timestamp CURRENT_TIME = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception
	{
		Configuration cfg = new Configuration().configure();
		new SchemaExport(cfg).create(true, true);
		factory = cfg.buildSessionFactory();
		session = factory.openSession();
		calendar = Calendar.getInstance();
		CURRENT_TIME = new Timestamp(calendar.getTimeInMillis());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception
	{
		if (session != null)
			session.close();
	}

}
