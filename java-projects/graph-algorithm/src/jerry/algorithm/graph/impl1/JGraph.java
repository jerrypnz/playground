package jerry.algorithm.graph.impl1;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jerry.algorithm.graph.Edge;
import jerry.algorithm.graph.Graph;

final class JEdge<VertexType> implements Edge<VertexType>
{
	private int weight;

	private Object attachment;

	private VertexType start;

	private VertexType end;

	public JEdge(VertexType start, VertexType end, int weight, Object attachment)
	{
		this.start = start;
		this.end = end;
		this.weight = weight;
		this.attachment = attachment;
	}

	public Object attachment()
	{
		return attachment;
	}

	public VertexType getEnd()
	{
		return end;
	}

	public VertexType getStart()
	{
		return start;
	}

	public int weight()
	{
		return weight;
	}

	public void setAttachment(Object attachment)
	{
		this.attachment = attachment;
	}

	public void setWeight(int weight)
	{
		this.weight = weight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((start == null) ? 0 : start.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final JEdge other = (JEdge) obj;
		if(this.start == other.start &&
				this.end == other.end)
			return true;
		else
			return false;
	}

}

final class VertexInfo<VertexType>
{
	public int inDegree = 0;

	public int outDegree = 0;

	public Set<Edge<VertexType>> outAjacenceSet = new HashSet<Edge<VertexType>>();
	
	public Set<Edge<VertexType>> inAjacenceSet = new HashSet<Edge<VertexType>>();
}

public class JGraph<VertexType> implements Graph<VertexType>
{
	public final int DEFAULT_WEIGHT = -99;
	
	private final String ILLEGAL_EXCEPTION = "Vertex is not in the graph";
	
	private final String SAME_VERTEX_EXCEPTION = "The two vertexes are the same";

	private boolean directed;

	private Map<VertexType, VertexInfo<VertexType>> graphMap = new HashMap<VertexType, VertexInfo<VertexType>>();

	public JGraph(boolean directed)
	{
		this.directed = directed;
	}
	
	public JGraph()
	{
		this.directed = true;
	}

	public boolean addEdge(VertexType start, VertexType end, int weight,
			Object attachment)
	{
		if (!graphMap.containsKey(start) && !graphMap.containsKey(end))
			throw new IllegalArgumentException(ILLEGAL_EXCEPTION);
		else if(start==end || start.equals(end))
			throw new IllegalArgumentException(SAME_VERTEX_EXCEPTION);
		
		VertexInfo<VertexType> startVertexInfo = graphMap.get(start);
		VertexInfo<VertexType> endVertexInfo = graphMap.get(end);
		JEdge<VertexType> edge = new JEdge<VertexType>(start, end, weight,
				attachment);
		if (startVertexInfo.outAjacenceSet.contains(edge))
			return false;
		startVertexInfo.outAjacenceSet.add(edge);
		endVertexInfo.inAjacenceSet.add(edge);
		startVertexInfo.outDegree ++;
		endVertexInfo.inDegree ++;
		if (!directed)
		{
			JEdge<VertexType> anotherEdge = new JEdge<VertexType>(end, start,
					weight, attachment);
			endVertexInfo.outAjacenceSet.add(anotherEdge);
			startVertexInfo.inAjacenceSet.add(anotherEdge);
			startVertexInfo.inDegree++;
			endVertexInfo.outDegree++;
		}
		
		return true;
	}

	public boolean addEdge(VertexType start, VertexType end, Object attachment)
	{
		return addEdge(start, end, DEFAULT_WEIGHT, attachment);
	}

	public boolean addEdge(VertexType start, VertexType end, int weight)
	{
		return addEdge(start, end, weight, null);
	}

	public boolean addEdge(VertexType start, VertexType end)
	{
		return addEdge(start, end, DEFAULT_WEIGHT, null);
	}

	public boolean addVertex(VertexType vertex)
	{
		if (graphMap.containsKey(vertex))
			return false;
		else
		{
			VertexInfo<VertexType> newVertexInfo = new VertexInfo<VertexType>();
			graphMap.put(vertex, newVertexInfo);
			return true;
		}
	}

	public Set<Edge<VertexType>> inAdjacenceSet(VertexType vertex)
	{
		if (graphMap.containsKey(vertex))
			return graphMap.get(vertex).inAjacenceSet;
		else
			throw new IllegalArgumentException(ILLEGAL_EXCEPTION);
	}
	
	public Set<Edge<VertexType>> outAdjacenceSet(VertexType vertex)
	{
		if (graphMap.containsKey(vertex))
			return graphMap.get(vertex).outAjacenceSet;
		else
			throw new IllegalArgumentException(ILLEGAL_EXCEPTION);
	}

	public boolean isDirected()
	{
		return directed;
	}

	public Set<VertexType> vertexSet()
	{
		return graphMap.keySet();
	}
	
	public int vertexNum()
	{
		return graphMap.keySet().size();
	}


	public boolean containsEdge(VertexType start, VertexType end)
	{
		JEdge<VertexType> edge = new JEdge<VertexType>(start, end,
				DEFAULT_WEIGHT, null);
		VertexInfo startVertexInfo = graphMap.get(start);
		return startVertexInfo.outAjacenceSet.contains(edge);
	}

	public boolean containsVertex(VertexType vertex)
	{
		return graphMap.containsKey(vertex);
	}

	/*
	 * public boolean isAdjacent(VertexType start, VertexType end) { // TODO
	 * Auto-generated method stub return false; }
	 */

	public int inDegree(VertexType vertex)
	{
		if (!graphMap.containsKey(vertex))
			throw new IllegalArgumentException(ILLEGAL_EXCEPTION);
		else
			return graphMap.get(vertex).inDegree;
	}

	public int outDegree(VertexType vertex)
	{
		if (!graphMap.containsKey(vertex))
			throw new IllegalArgumentException(ILLEGAL_EXCEPTION);
		else
			return graphMap.get(vertex).outDegree;
	}

	public Edge<VertexType> getEdge(VertexType start, VertexType end)
	{
		if(!containsVertex(start) || !containsVertex(end))
			throw new IllegalArgumentException(ILLEGAL_EXCEPTION);
		VertexInfo<VertexType> startVertexInfo = graphMap.get(start);
		Edge<VertexType> edge = null;
		Iterator<Edge<VertexType>> it = startVertexInfo.outAjacenceSet.iterator();
		while(it.hasNext())
		{
			Edge<VertexType> current = it.next();
			if(current.getEnd() == end)
			{
				edge = current;
				break;
			}
		}
		return edge;
	}

	
	

}
