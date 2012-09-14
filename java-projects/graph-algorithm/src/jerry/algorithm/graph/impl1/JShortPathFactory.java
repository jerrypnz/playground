package jerry.algorithm.graph.impl1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jerry.algorithm.graph.Edge;
import jerry.algorithm.graph.Graph;
import jerry.algorithm.graph.Path;
import jerry.algorithm.graph.ShortPathFactory;

class JShortPath<VertexType> implements Path<VertexType>
{
	private List<VertexType> shortPathVertexes = new ArrayList<VertexType>();

	public void addToHead(VertexType vertex)
	{
		shortPathVertexes.add(0, vertex);
	}

	public void addToTail(VertexType vertex)
	{
		shortPathVertexes.add(vertex);
	}

	public VertexType getEnd()
	{
		return shortPathVertexes.get(shortPathVertexes.size() - 1);
	}

	public List<VertexType> getPathVertexes()
	{
		return shortPathVertexes;
	}

	public VertexType getStart()
	{
		return shortPathVertexes.get(0);
	}

}

class ShortPathInfo<VertexType>
{
	public static final int NO_PATH = 210000000;

	public VertexType priorVertex;

	public int pathLength;

	public boolean isInShortPath;
}

public class JShortPathFactory<VertexType> implements
		ShortPathFactory<VertexType>
{

	private Map<VertexType, ShortPathInfo<VertexType>> pathMap = null;

	private VertexType currentStartVertex = null;

	protected void buildPathMap(VertexType start, Graph<VertexType> graph)
	{
		currentStartVertex = start;
		pathMap = new HashMap<VertexType, ShortPathInfo<VertexType>>();
		for (VertexType v : graph.vertexSet())
		{
			ShortPathInfo<VertexType> info = new ShortPathInfo<VertexType>();
			info.priorVertex = start;
			if (v == start)
				info.isInShortPath = true;
			else
				info.isInShortPath = false;
			if (graph.containsEdge(start, v))
				info.pathLength = graph.getEdge(start, v).weight();
			else
				info.pathLength = ShortPathInfo.NO_PATH;

			pathMap.put(v, info);
		}
	}

	public Path<VertexType> shortPath(Graph<VertexType> graph,
			VertexType start, VertexType end)
	{
		if(!graph.containsVertex(start) || !graph.containsVertex(end))
			throw new IllegalArgumentException("Start or end vertex is not in the graph");
		JShortPath<VertexType> result = new JShortPath<VertexType>();
		if(start != currentStartVertex || pathMap == null)
			buildPathMap(start, graph);
		for (VertexType c = findShortest(pathMap); c != end; c = findShortest(pathMap))
		{
			pathMap.get(c).isInShortPath = true;
			for (VertexType v : pathMap.keySet())
			{
				if (!graph.containsEdge(c, v))
					continue;
				Edge<VertexType> temp = graph.getEdge(c, v);
				ShortPathInfo<VertexType> cInfo = pathMap.get(c);
				ShortPathInfo<VertexType> vInfo = pathMap.get(v);
				if (temp.weight() + cInfo.pathLength < vInfo.pathLength)
				{
					vInfo.pathLength = temp.weight() + cInfo.pathLength;
					vInfo.priorVertex = c;
				}
			}
		}
		for (VertexType v = end; v != start; v = pathMap.get(v).priorVertex)
		{
			result.addToHead(v);
		}
		result.addToHead(start);
		return result;
	}

	private VertexType findShortest(
			Map<VertexType, ShortPathInfo<VertexType>> pathMap)
	{
		int shortest = ShortPathInfo.NO_PATH;
		VertexType shortestVertex = null;
		for (VertexType v : pathMap.keySet())
		{
			if (pathMap.get(v).isInShortPath)
				continue;
			if (pathMap.get(v).pathLength < shortest)
			{
				shortestVertex = v;
				shortest = pathMap.get(v).pathLength;
			}
		}
		return shortestVertex;
	}

}
