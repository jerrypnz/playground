package jerry.algorithm.graph.impl1;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.*;
import java.util.Map;
import java.util.Queue;

import jerry.algorithm.graph.*;

public class JGraphIteratorFactory<VertexType> implements
		GraphIteratorFactory<VertexType>
{
	//将Graph，访问标志和结果放在这里，使DFS方法的参数个数大大减少，节省了堆栈空间
	private Graph<VertexType> graph;
	private Map<VertexType, Boolean> isVisited;
	private List<VertexType> results;
	/*
	 * (non-Javadoc)
	 * 
	 * @see jerry.algorithm.graph.impl1.GraphIteratorFactory#DFSIterator(jerry.algorithm.graph.Graph)
	 */
	public Iterator<VertexType> DFSIterator(Graph<VertexType> graph,
			VertexType start)
	{
		this.graph = graph;
		results = new ArrayList<VertexType>();
		isVisited = new HashMap<VertexType, Boolean>();
		Iterator<VertexType> it = graph.vertexSet().iterator();
		while (it.hasNext())
		{
			isVisited.put(it.next(), false);
		}
		results.add(start);
		isVisited.put(start, true);
		DFS(start);
		return results.iterator();
	}

	//Depth First Traversal procedure.
	protected void DFS(VertexType start)
	{
		Iterator<Edge<VertexType>> it = graph.outAdjacenceSet(start).iterator();
		while(it.hasNext())
		{
			VertexType endVertex = it.next().getEnd();
			Boolean vertexVisited = isVisited.get(endVertex);
			if(!vertexVisited)
			{
				results.add(endVertex);
				isVisited.put(endVertex, true);
				DFS(endVertex);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see jerry.algorithm.graph.impl1.GraphIteratorFactory#BFSIterator(jerry.algorithm.graph.Graph)
	 */
	public Iterator<VertexType> BFSIterator(Graph<VertexType> graph,
			VertexType start)
	{
		this.graph = graph;		
		BFS(start);
		return results.iterator();
	}
	
	protected void BFS(VertexType start)
	{
		results = new ArrayList<VertexType>();
		isVisited = new HashMap<VertexType, Boolean>();
		Iterator<VertexType> allIterator = graph.vertexSet().iterator();
		while (allIterator.hasNext())
			isVisited.put(allIterator.next(), false);
		Queue<VertexType> bfsQueue = new ArrayDeque<VertexType>();
		bfsQueue.add(start);
		isVisited.put(start, true);
		while(!bfsQueue.isEmpty())
		{
			VertexType currentV = bfsQueue.poll();
			results.add(currentV);
			Iterator<Edge<VertexType>> it = graph.outAdjacenceSet(currentV).iterator();
			while(it.hasNext())
			{
				VertexType endVertex = it.next().getEnd();
				Boolean vertexVisited = isVisited.get(endVertex);
				if(!vertexVisited)
				{
					bfsQueue.add(endVertex);
					isVisited.put(endVertex, true);
				}
			}
		}
		
	}

}
