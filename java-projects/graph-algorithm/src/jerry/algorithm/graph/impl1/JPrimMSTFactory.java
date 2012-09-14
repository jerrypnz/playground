package jerry.algorithm.graph.impl1;

import jerry.algorithm.graph.Edge;
import jerry.algorithm.graph.Graph;
import jerry.algorithm.graph.MSTFactory;

class CloseEdgeBean
{
	public static final int NOT_CONNECTED = 210000000;

	public static final int IN_MST = 210000001;

	private Object vertex;

	private Object adjVertex;

	private int lowWeight;

	private Object attachment;

	/**
	 * @param vertex
	 * @param adjVertex
	 * @param lowWeight
	 * @param attachment
	 */
	public CloseEdgeBean(Object vertex, Object adjVertex, int lowWeight,
			Object attachment)
	{
		this.vertex = vertex;
		this.adjVertex = adjVertex;
		this.lowWeight = lowWeight;
		this.attachment = attachment;
	}

	/**
	 * Empty constructor
	 */
	public CloseEdgeBean()
	{
	}

	/**
	 * @return the adjVertex
	 */
	public Object getAdjVertex()
	{
		return adjVertex;
	}

	/**
	 * @param adjVertex
	 *            the adjVertex to set
	 */
	public void setAdjVertex(Object adjVertex)
	{
		this.adjVertex = adjVertex;
	}

	/**
	 * @return the attachment
	 */
	public Object getAttachment()
	{
		return attachment;
	}

	/**
	 * @param attachment
	 *            the attachment to set
	 */
	public void setAttachment(Object attachment)
	{
		this.attachment = attachment;
	}

	/**
	 * @return the lowWeight
	 */
	public int getLowWeight()
	{
		return lowWeight;
	}

	/**
	 * @param lowWeight
	 *            the lowWeight to set
	 */
	public void setLowWeight(int lowWeight)
	{
		this.lowWeight = lowWeight;
	}

	/**
	 * @return the vertex
	 */
	public Object getVertex()
	{
		return vertex;
	}

	/**
	 * @param vertex
	 *            the vertex to set
	 */
	public void setVertex(Object vertex)
	{
		this.vertex = vertex;
	}
}

public class JPrimMSTFactory<VertexType> implements
		MSTFactory<VertexType>
{

	protected int findLowest(CloseEdgeBean[] close)
	{
		int result = 0;
		for (int i = 1; i < close.length; i++)
		{
			if (close[i].getLowWeight() == CloseEdgeBean.IN_MST)
				continue;
			else if (close[i].getLowWeight() < close[result].getLowWeight())
				result = i;
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	protected void updateCloseEdge(CloseEdgeBean[] close, VertexType newAdded,
			Graph<VertexType> graph)
	{
		for (int i = 0; i < close.length; i++)
		{
			Edge<VertexType> temp = graph.getEdge(newAdded,
					(VertexType) close[i].getVertex());
			if (temp == null || close[i].getLowWeight() == CloseEdgeBean.IN_MST)
				continue;
			if (temp.weight() < close[i].getLowWeight())
			{
				close[i].setAdjVertex(newAdded);
				close[i].setLowWeight(temp.weight());
				close[i].setAttachment(temp.attachment());
			}
		}
	}

	/*protected void printArray(CloseEdgeBean[] close)
	{
		for (int i = 0; i < close.length; i++)
		{
			System.out.print(close[i].getAdjVertex() + ":"
					+ close[i].getVertex() + ":" + close[i].getLowWeight()
					+ "  ");
		}
		System.out.println();
	}*/

	@SuppressWarnings("unchecked")
	public Graph<VertexType> miniSpanTree(Graph<VertexType> graph)
	{
		if (graph.isDirected())
			throw new IllegalArgumentException("Graph is directed.");
		// 建立辅助数组，记录从U到U-V的所有边的最小权值
		CloseEdgeBean[] close = new CloseEdgeBean[graph.vertexNum() - 1];
		JGraph<VertexType> result = new JGraph<VertexType>(false);
		// 初始化辅助数组
		int i = 0;
		VertexType start = graph.vertexSet().iterator().next();
		result.addVertex(start);
		for (VertexType current : graph.vertexSet())
		{
			if (current == start)
				continue;
			int lowWeight = CloseEdgeBean.NOT_CONNECTED;
			Object attachment = null;
			if (graph.containsEdge(start, current))
			{
				Edge<VertexType> edge = graph.getEdge(start, current);
				lowWeight = edge.weight();
				attachment = edge.attachment();
			}
			close[i++] = new CloseEdgeBean(current, start, lowWeight,
					attachment);
		}
		// 进入运算过程
		for (int j = 1; j < graph.vertexNum(); j++)
		{
			//printArray(close);
			int closest = findLowest(close);
			result.addVertex((VertexType) close[closest].getVertex());
			result.addEdge((VertexType) close[closest].getAdjVertex(),
					(VertexType) close[closest].getVertex(), close[closest]
							.getLowWeight(), close[closest].getAttachment());
			close[closest].setLowWeight(CloseEdgeBean.IN_MST);
			updateCloseEdge(close, (VertexType) close[closest].getVertex(),
					graph);
		}
		return result;

	}

}
