package jerry.algorithm.graph.test;

import java.util.Set;
import jerry.algorithm.graph.Edge;
import jerry.algorithm.graph.Graph;
import jerry.algorithm.graph.impl1.JGraph;
import jerry.algorithm.graph.util.GraphHelper;
import junit.framework.TestCase;

public class JGraphTest extends TestCase
{

	public void testAddVertex()
	{
		Graph<Integer> graph = new JGraph<Integer>(true);
		//Define the vertexes
		Integer v1 = new Integer(1);
		Integer v2 = new Integer(2);
		Integer v3 = new Integer(3);
		Integer v4 = new Integer(4);
		//Add these vertexes to the graph,and assert the return value is "true"
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));
		assertTrue(graph.addVertex(v4));
		//Assert the return value is "false" when add a vertex that already exists.
		assertFalse(graph.addVertex(v4));
		
		//Test if the vertexes are in the graph
		assertTrue(graph.containsVertex(v1));
		assertTrue(graph.containsVertex(v2));
		assertTrue(graph.containsVertex(v3));
		assertTrue(graph.containsVertex(v4));
		
		//Test vertex set
		Set<Integer> vertexes = graph.vertexSet();
		assertEquals(4,vertexes.size());
		assertTrue(vertexes.contains(v1));
		assertTrue(vertexes.contains(v2));
		assertTrue(vertexes.contains(v3));
		assertTrue(vertexes.contains(v4));
		
		//Add edges to the graph,and assert the return value is true.
		assertTrue(graph.addEdge(v1, v2));
		assertTrue(graph.addEdge(v1, v3));
		assertTrue(graph.addEdge(v1, v4));
		assertTrue(graph.addEdge(v2, v4));
		//Assert the return value is "false" when add an edge that already exists.
		assertFalse(graph.addEdge(v2, v4));
		//Test adjacence
		
		assertTrue(graph.containsEdge(v1, v2));
		assertTrue(graph.containsEdge(v1, v3));
		assertTrue(graph.containsEdge(v1, v4));
		assertTrue(graph.containsEdge(v2, v4));
		assertFalse(graph.containsEdge(v3, v4));
		assertFalse(graph.containsEdge(v2, v1));
		Edge<Integer> edge1 = graph.getEdge(v1, v2);
		assertNotNull(edge1);
		assertEquals(v1,edge1.getStart());
		assertEquals(v2,edge1.getEnd());
		assertNull(graph.getEdge(v2, v3));
		//Test in degree
		assertEquals(3,graph.outDegree(v1));
		assertEquals(1,graph.outDegree(v2));
		assertEquals(0,graph.outDegree(v3));
		assertEquals(0,graph.outDegree(v4));
		//Test out degree
		assertEquals(0,graph.inDegree(v1));
		assertEquals(1,graph.inDegree(v2));
		assertEquals(1,graph.inDegree(v3));
		assertEquals(2,graph.inDegree(v4));
		GraphHelper.printGraph(graph,true,false);
	}
		
}
