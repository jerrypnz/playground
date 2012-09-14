package com.jstudio.rest.test;

import java.sql.Timestamp;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestTimeformat
{
	@Test
	public void testTimeFormat()
	{
		Timestamp time = Timestamp.valueOf("2008-05-06 11:05:54");
		assertNotNull(time);
		System.out.println(time);
	}
}
