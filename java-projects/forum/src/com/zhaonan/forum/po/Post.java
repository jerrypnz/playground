package com.zhaonan.forum.po;

import java.sql.Timestamp;

public class Post
{
	private int id;

	private String content;

	private int userId;

	private int topicId;
	
	private Timestamp postTime;

	/**
	 * @return the topidId
	 */
	public int getTopicId()
	{
		return topicId;
	}

	/**
	 * @param topidId
	 *            the topidId to set
	 */
	public void setTopicId(int topidId)
	{
		this.topicId = topidId;
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
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @return the userId
	 */
	public int getUserId()
	{
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(int userId)
	{
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + topicId;
		result = prime * result + userId;
		return result;
	}

	/* (non-Javadoc)
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
		final Post other = (Post) obj;
		if (id != other.id)
			return false;
		if (topicId != other.topicId)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	/**
	 * @return the publishTime
	 */
	public Timestamp getPostTime()
	{
		return postTime;
	}

	/**
	 * @param publishTime the publishTime to set
	 */
	public void setPostTime(Timestamp postTime)
	{
		this.postTime = postTime;
	}

}
