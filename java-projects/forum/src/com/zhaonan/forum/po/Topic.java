package com.zhaonan.forum.po;

import java.sql.Timestamp;

public class Topic
{
	private int id;

	private int boardId;
	
	private int userId;

	private String title;

	private Timestamp postTime;
	
	private int readTimes;
	
	private int priority;
	

	/**
	 * @return the priority
	 */
	public int getPriority()
	{
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(int priority)
	{
		this.priority = priority;
	}

	/**
	 * @return the readTimes
	 */
	public int getReadTimes()
	{
		return readTimes;
	}

	/**
	 * @param readTimes the readTimes to set
	 */
	public void setReadTimes(int readTimes)
	{
		this.readTimes = readTimes;
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
	 * @return the boardId
	 */
	public int getBoardId()
	{
		return boardId;
	}

	/**
	 * @param boardId
	 *            the boardId to set
	 */
	public void setBoardId(int boardId)
	{
		this.boardId = boardId;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the postTime
	 */
	public Timestamp getPostTime()
	{
		return postTime;
	}

	/**
	 * @param postTime
	 *            the postTime to set
	 */
	public void setPostTime(Timestamp postTime)
	{
		this.postTime = postTime;
	}

	/**
	 * @return the userId
	 */
	public int getUserId()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
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
		result = prime * result + boardId;
		result = prime * result + id;
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
		final Topic other = (Topic) obj;
		if (boardId != other.boardId)
			return false;
		if (id != other.id)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

}
