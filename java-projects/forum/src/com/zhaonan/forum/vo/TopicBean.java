package com.zhaonan.forum.vo;

import java.sql.Timestamp;

public class TopicBean
{
	private int id;

	private int boardId;

	private String title;

	private String content;

	private Timestamp postTime;
	
	private String authorName;
	
	private int authorId;
	
	private int readTimes;
	
	private int replyTimes;
	
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
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
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
	 * @param boardId the boardId to set
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
	 * @param title the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @return the postTime
	 */
	public Timestamp getPostTime()
	{
		return postTime;
	}

	/**
	 * @param postTime the postTime to set
	 */
	public void setPostTime(Timestamp postTime)
	{
		this.postTime = postTime;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName()
	{
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName)
	{
		this.authorName = authorName;
	}

	/**
	 * @return the authorId
	 */
	public int getAuthorId()
	{
		return authorId;
	}

	/**
	 * @param authorId the authorId to set
	 */
	public void setAuthorId(int authorId)
	{
		this.authorId = authorId;
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
	 * @return the replyTimes
	 */
	public int getReplyTimes()
	{
		return replyTimes;
	}

	/**
	 * @param replyTimes the replyTimes to set
	 */
	public void setReplyTimes(int replyTimes)
	{
		this.replyTimes = replyTimes;
	}
	
	

}
