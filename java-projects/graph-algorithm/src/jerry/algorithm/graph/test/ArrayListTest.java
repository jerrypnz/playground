package jerry.algorithm.graph.test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class ArrayListTest extends TestCase
{

	public void testArrayList()
	{
		List<Integer> list = new ArrayList<Integer>();
		list.add(0, 1);
		list.add(0, 2);
		list.add(0, 3);
		list.add(0, 4);
		
		assertTrue(list.get(0)==4);
		assertTrue(list.get(1)==3);
		assertTrue(list.get(2)==2);
		assertTrue(list.get(3)==1);
		
	}

}
