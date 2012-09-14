package jerry.algorithm.graph;

import java.util.Set;

public interface Graph<VertexType>
{
	public boolean addVertex(VertexType vertex);

	public boolean addEdge(VertexType start, VertexType end, int weight,
			Object attachment);

	public boolean addEdge(VertexType start, VertexType end, Object attachment);

	public boolean addEdge(VertexType start, VertexType end, int weight);

	public boolean addEdge(VertexType start, VertexType end);
	
	public boolean containsVertex(VertexType vertex);
	
	public boolean containsEdge(VertexType start,VertexType end);
	
	public Edge<VertexType> getEdge(VertexType start,VertexType end);

	public boolean isDirected();
	
	//public boolean isAdjacent(VertexType start,VertexType end);
	
	public int inDegree(VertexType vertex);
	
	public int outDegree(VertexType vertex);

	public Set<VertexType> vertexSet();
	
	public int vertexNum();

	public Set<Edge<VertexType>> inAdjacenceSet(VertexType vertex);
	
	public Set<Edge<VertexType>> outAdjacenceSet(VertexType vertex);
}
