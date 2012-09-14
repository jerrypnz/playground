package jerry.algorithm.graph.util;

import java.util.Iterator;
import java.util.Set;

import jerry.algorithm.graph.Edge;
import jerry.algorithm.graph.Graph;
import jerry.algorithm.graph.Path;

public class GraphHelper
{
	@SuppressWarnings("unchecked")
	public static void printGraph(Graph graph,boolean showWeight,boolean showAttachment)
	{
		Set vertexes = graph.vertexSet();
		for(Iterator it=vertexes.iterator();it.hasNext();)
		{
			Object current = it.next();
			System.out.println("-------------------------------");
			System.out.println("Vertex:" + current);
			Set inAdjacences = graph.inAdjacenceSet(current);
			Set outAdjacences = graph.outAdjacenceSet(current);
			
			System.out.println("Out adjacences:");
			for(Iterator o=outAdjacences.iterator();o.hasNext();)
			{
				Edge edge = (Edge)o.next();
				System.out.print( edge.getStart()+ "---->" + edge.getEnd());
				if(showWeight)
					System.out.print("   weight:" + edge.weight());
				if(showAttachment)
					System.out.print("   attachment:" + edge.attachment());
				System.out.println();
			}
			
			if(!graph.isDirected())
				continue;
			
			System.out.println("In adjacences:");
			for(Iterator i=inAdjacences.iterator();i.hasNext();)
			{
				Edge edge = (Edge)i.next();
				System.out.print( edge.getStart()+ "---->" + edge.getEnd());
				if(showWeight)
					System.out.print("   weight:" + edge.weight());
				if(showAttachment)
					System.out.print("   attachment:" + edge.attachment());
				System.out.println();
			}
			
		}
	}
	
	public static void printPath(Path path)
	{
		System.out.println("----------------------------------------------");
		System.out.println("Path from " + path.getStart() + " to " + path.getEnd() + ":");
		System.out.print("start ---> ");
		for(Object current:path.getPathVertexes())
		{
			System.out.print(current + " ---> ");
		}
		System.out.println("end");
	}

}
