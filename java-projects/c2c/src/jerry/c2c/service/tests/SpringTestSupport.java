package jerry.c2c.service.tests;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.TestCase;

public abstract class SpringTestSupport extends TestCase
{
	protected ApplicationContext context = null;

	protected void setUp() throws Exception
	{
		Configuration cfg = new Configuration().configure();
		new SchemaExport(cfg).create(true, true);
		context = new ClassPathXmlApplicationContext("applicationContext.xml");
	}
}
