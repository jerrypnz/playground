package jerry.algorithm.graph.test;

import java.util.Iterator;

import jerry.algorithm.graph.Graph;
import jerry.algorithm.graph.GraphIteratorFactory;
import jerry.algorithm.graph.impl1.JGraph;
import jerry.algorithm.graph.impl1.JGraphIteratorFactory;
import junit.framework.TestCase;


public class VisitTest extends TestCase
{
	public void testDFSIterator()
	{
		Graph<Integer> graph = new JGraph<Integer>(true);
		//Define the vertexes
		Integer v1 = new Integer(1);
		Integer v2 = new Integer(2);
		Integer v3 = new Integer(3);
		Integer v4 = new Integer(4);
		Integer v5 = new Integer(5);
		Integer v6 = new Integer(6);
		Integer v7 = new Integer(7);
		Integer v8 = new Integer(8);
		
        //Add these vertexes to the graph,and assert the return value is "true"
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));
		assertTrue(graph.addVertex(v4));
		assertTrue(graph.addVertex(v5));
		assertTrue(graph.addVertex(v6));
		assertTrue(graph.addVertex(v7));
		assertTrue(graph.addVertex(v8));
		
		//Add edges to the graph,and assert the return value is true.
		assertTrue(graph.addEdge(v1, v2));
		assertTrue(graph.addEdge(v1, v3));
		assertTrue(graph.addEdge(v1, v4));
		assertTrue(graph.addEdge(v2, v5));
		assertTrue(graph.addEdge(v3, v4));
		assertTrue(graph.addEdge(v3, v7));
		assertTrue(graph.addEdge(v4, v6));
		assertTrue(graph.addEdge(v5, v7));
		assertTrue(graph.addEdge(v6, v7));
		assertTrue(graph.addEdge(v6, v8));
		assertTrue(graph.addEdge(v7, v8));
		
		GraphIteratorFactory<Integer> itFactory = new JGraphIteratorFactory<Integer>();
		Iterator<Integer> it = itFactory.DFSIterator(graph, v1);
		assertNotNull(it);
		System.out.println("DFS visit results:");
		System.out.print("start --> ");
		while(it.hasNext())
		{
			System.out.print(it.next() + " --> ");
		}
		System.out.println("end");
		
		Iterator<Integer> it2 = itFactory.BFSIterator(graph, v1);
		assertNotNull(it2);
		System.out.println("BFS visit results:");
		System.out.print("start --> ");
		while(it2.hasNext())
		{
			System.out.print(it2.next() + " --> ");
		}
		System.out.println("end");
		/*assertTrue("There is nothing in the iterator",it.hasNext());
		assertEquals(v1,it.next());
		assertEquals(v4,it.next());
		assertEquals(v3,it.next());
		assertEquals(v2,it.next());
		assertFalse("There is more than 4 elements in the iterator",it.hasNext());*/
	}

}
