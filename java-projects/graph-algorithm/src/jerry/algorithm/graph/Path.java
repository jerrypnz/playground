package jerry.algorithm.graph;

import java.util.List;

public interface Path<VertexType>
{
	public VertexType getStart();
	public VertexType getEnd();
	public List<VertexType> getPathVertexes();
}
