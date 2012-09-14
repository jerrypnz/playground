package com.zhaonan.forum.vo;


public class BoardBean
{
	private int id;

	private String name;

	private String description;
	
	private int topicNum;
	
	private int postNum;
	
	private int latestTopicId = -1;
	
	private String latestTopicTitle = "";


	/**
	 * @return the topicNum
	 */
	public int getTopicNum()
	{
		return topicNum;
	}

	/**
	 * @param topicNum the topicNum to set
	 */
	public void setTopicNum(int topicNum)
	{
		this.topicNum = topicNum;
	}

	/**
	 * @return the postNum
	 */
	public int getPostNum()
	{
		return postNum;
	}

	/**
	 * @param postNum the postNum to set
	 */
	public void setPostNum(int postNum)
	{
		this.postNum = postNum;
	}

	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}


	/**
	 * @return the latestTopicId
	 */
	public int getLatestTopicId()
	{
		return latestTopicId;
	}

	/**
	 * @param latestTopicId the latestTopicId to set
	 */
	public void setLatestTopicId(int latestTopicId)
	{
		this.latestTopicId = latestTopicId;
	}

	/**
	 * @return the latestTopicTitle
	 */
	public String getLatestTopicTitle()
	{
		return latestTopicTitle;
	}

	/**
	 * @param latestTopicTitle the latestTopicTitle to set
	 */
	public void setLatestTopicTitle(String latestTopicTitle)
	{
		this.latestTopicTitle = latestTopicTitle;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + postNum;
		result = prime * result + topicNum;
		return result;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final BoardBean other = (BoardBean) obj;
		if (id != other.id)
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		if (postNum != other.postNum)
			return false;
		if (topicNum != other.topicNum)
			return false;
		return true;
	}

}
