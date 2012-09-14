package jerry.algorithm.graph.test;

import java.util.List;

import jerry.algorithm.graph.Graph;
import jerry.algorithm.graph.Path;
import jerry.algorithm.graph.impl1.JGraph;
import jerry.algorithm.graph.impl1.JShortPathFactory;
import jerry.algorithm.graph.util.GraphHelper;
import junit.framework.TestCase;

public class JShortPathTest extends TestCase
{
	public void testShortPath()
	{
		Graph<String> graph = new JGraph<String>(true);
		
		String v1 = "A";
		String v2 = "B";
		String v3 = "C";
		String v4 = "D";
		String v5 = "E";
		String v6 = "F";
		
		assertTrue(graph.addVertex(v1));
		assertTrue(graph.addVertex(v2));
		assertTrue(graph.addVertex(v3));
		assertTrue(graph.addVertex(v4));
		assertTrue(graph.addVertex(v5));
		assertTrue(graph.addVertex(v6));
		
		assertTrue(graph.addEdge(v1, v2, 2));
		assertTrue(graph.addEdge(v1, v3, 100));
		assertTrue(graph.addEdge(v2, v3, 100));
		assertTrue(graph.addEdge(v2, v4, 3));
		assertTrue(graph.addEdge(v2, v5, 100));
		assertTrue(graph.addEdge(v3, v5, 100));
		assertTrue(graph.addEdge(v4, v5, 7));
		assertTrue(graph.addEdge(v4, v6, 100));
		assertTrue(graph.addEdge(v5, v6, 7));
		
		JShortPathFactory<String> factory = new JShortPathFactory<String>();
		Path<String> result1 = factory.shortPath(graph, v1, v5);
		Path<String> result = factory.shortPath(graph,v1,v6);
		List<String> pathVertexes1 = result1.getPathVertexes();
		List<String> pathVertexes = result.getPathVertexes();
		assertEquals(5,pathVertexes.size());
		assertEquals(v1,result.getStart());
		assertEquals(v6,result.getEnd());
		assertEquals(v1,pathVertexes.get(0));
		assertEquals(v2,pathVertexes.get(1));
		assertEquals(v4,pathVertexes.get(2));
		assertEquals(v5,pathVertexes.get(3));
		assertEquals(v6,pathVertexes.get(4));
		
		assertEquals(4,pathVertexes1.size());
		assertEquals(v1,result1.getStart());
		assertEquals(v5,result1.getEnd());
		assertEquals(v1,pathVertexes1.get(0));
		assertEquals(v2,pathVertexes1.get(1));
		assertEquals(v4,pathVertexes1.get(2));
		assertEquals(v5,pathVertexes1.get(3));
		
		GraphHelper.printPath(result1);
		GraphHelper.printPath(result);
	}

}
