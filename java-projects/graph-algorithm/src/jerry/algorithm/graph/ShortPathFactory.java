package jerry.algorithm.graph;

public interface ShortPathFactory<VertexType>
{
	Path<VertexType> shortPath(Graph<VertexType> graph, VertexType start, VertexType end);
}
