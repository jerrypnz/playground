package jerry.c2c.service.tests;

import java.sql.Timestamp;

import jerry.c2c.util.DateTimeUtil;
import junit.framework.TestCase;

public class TestTimestamp extends TestCase
{

	protected void setUp() throws Exception
	{
		super.setUp();
	}
	
	public void testTimestamp()
	{
		try
		{
			Timestamp current1 = DateTimeUtil.getCurrentTimestamp();
			Thread.sleep(3000);
			Timestamp current2 = DateTimeUtil.getCurrentTimestamp();
			assertTrue(current2.compareTo(current1)>0);
			assertTrue(current1.before(current2));
			assertTrue(current2.after(current1));
		}
		catch (InterruptedException e)
		{
			fail("Exception:" + e);
			e.printStackTrace();
		}
	}

}
