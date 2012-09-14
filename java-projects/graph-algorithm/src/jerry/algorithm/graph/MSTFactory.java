package jerry.algorithm.graph;

public interface MSTFactory<VertexType>
{
	public Graph<VertexType> miniSpanTree(Graph<VertexType> graph);
}
