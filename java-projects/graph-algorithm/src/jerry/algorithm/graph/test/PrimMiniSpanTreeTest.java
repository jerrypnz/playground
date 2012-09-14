package jerry.algorithm.graph.test;


import jerry.algorithm.graph.Graph;
import jerry.algorithm.graph.MSTFactory;
import jerry.algorithm.graph.impl1.JGraph;
import jerry.algorithm.graph.impl1.JPrimMSTFactory;
import jerry.algorithm.graph.util.GraphHelper;
import junit.framework.TestCase;

public class PrimMiniSpanTreeTest extends TestCase
{
	public void testPrim()
	{
		Graph<Integer> graph = new JGraph<Integer>(false);
		// Define the vertexes
		Integer v1 = new Integer(1);
		Integer v2 = new Integer(2);
		Integer v3 = new Integer(3);
		Integer v4 = new Integer(4);
		Integer v5 = new Integer(5);
		Integer v6 = new Integer(6);

		// Add these vertexes to the graph,and assert the return value is "true"
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));
		assertTrue(graph.addVertex(v4));
		assertTrue(graph.addVertex(v5));
		assertTrue(graph.addVertex(v6));

		// Add edges to the graph,and assert the return value is true.
		assertTrue(graph.addEdge(v1, v2, 100, null));
		assertTrue(graph.addEdge(v1, v3, 1, null));
		assertTrue(graph.addEdge(v1, v4, 100, null));
		assertTrue(graph.addEdge(v2, v3, 2, null));
		assertTrue(graph.addEdge(v2, v5, 1, null));
		assertTrue(graph.addEdge(v3, v4, 100, null));
		assertTrue(graph.addEdge(v3, v5, 100, null));
		assertTrue(graph.addEdge(v3, v6, 4, null));
		assertTrue(graph.addEdge(v4, v6, 2, null));
		assertTrue(graph.addEdge(v5, v6, 100, null));
		
		MSTFactory<Integer> factory = new JPrimMSTFactory<Integer>();
		Graph<Integer> mst = factory.miniSpanTree(graph);
		
		assertTrue(mst.containsVertex(v1));
		assertTrue(mst.containsVertex(v2));
		assertTrue(mst.containsVertex(v3));
		assertTrue(mst.containsVertex(v4));
		assertTrue(mst.containsVertex(v5));
		assertTrue(mst.containsVertex(v6));
		
		assertTrue(mst.containsEdge(v1, v3));
		assertTrue(mst.containsEdge(v2, v3));
		assertTrue(mst.containsEdge(v2, v5));
		assertTrue(mst.containsEdge(v3, v6));
		assertTrue(mst.containsEdge(v4, v6));
		
		assertEquals(1,mst.getEdge(v1, v3).weight());
		assertEquals(2,mst.getEdge(v3, v2).weight());
		assertEquals(1,mst.getEdge(v5, v2).weight());
		assertEquals(4,mst.getEdge(v6, v3).weight());
		assertEquals(2,mst.getEdge(v6, v4).weight());
		
		assertEquals(1,mst.outDegree(v1));
		assertEquals(2,mst.outDegree(v2));
		assertEquals(3,mst.outDegree(v3));
		assertEquals(1,mst.outDegree(v4));
		assertEquals(1,mst.outDegree(v5));
		assertEquals(2,mst.outDegree(v6));
		
		assertEquals(1,mst.inDegree(v1));
		assertEquals(2,mst.inDegree(v2));
		assertEquals(3,mst.inDegree(v3));
		assertEquals(1,mst.inDegree(v4));
		assertEquals(1,mst.inDegree(v5));
		assertEquals(2,mst.inDegree(v6));
		
		System.out.println("\n\nThe original graph:");
		GraphHelper.printGraph(graph, true, true);
		System.out.println("\n\nThe Mini Span Tree of the graph:");
		GraphHelper.printGraph(mst, true, false);

	}

}
