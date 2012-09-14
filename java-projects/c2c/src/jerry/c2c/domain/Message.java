/**
 * 
 */
package jerry.c2c.domain;

import java.sql.Timestamp;

/**
 * @author Jerry
 *
 */
public class Message
{
	private long id;
	private User from;
	private User to;
	private String title;
	private String content;
	private Timestamp sendTime;
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
	 * @return the from
	 */
	public User getFrom()
	{
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(User from)
	{
		this.from = from;
	}
	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	/**
	 * @return the sendTime
	 */
	public Timestamp getSendTime()
	{
		return sendTime;
	}
	/**
	 * @param sendTime the sendTime to set
	 */
	public void setSendTime(Timestamp sendTime)
	{
		this.sendTime = sendTime;
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
	 * @return the to
	 */
	public User getTo()
	{
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(User to)
	{
		this.to = to;
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
		final Message other = (Message) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
