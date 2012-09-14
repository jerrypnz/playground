package jerry.algorithm.graph;

public interface Edge<VertexType>
{
	public VertexType getStart();
	public VertexType getEnd();
	public Object attachment();
	public void setAttachment(Object attachment);
	public int weight();
	public void setWeight(int weight);
	
}
