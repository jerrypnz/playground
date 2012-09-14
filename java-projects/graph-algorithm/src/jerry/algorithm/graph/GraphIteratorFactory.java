package jerry.algorithm.graph;

import java.util.Iterator;


public interface GraphIteratorFactory<VertexType>
{

	public abstract Iterator<VertexType> DFSIterator(Graph<VertexType> graph,VertexType start);

	public abstract Iterator<VertexType> BFSIterator(Graph<VertexType> graph,VertexType start);

}