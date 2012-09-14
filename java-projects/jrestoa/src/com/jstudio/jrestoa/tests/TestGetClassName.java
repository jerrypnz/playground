package com.jstudio.jrestoa.tests;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestGetClassName
{

	@Before
	public void setUp() throws Exception
	{
	}
	
	@Test
	public void testGetClassName()
	{
		assertEquals("Base",new Base().getClassName());
		assertEquals("Derived",new Derived().getClassName());
	}

}
