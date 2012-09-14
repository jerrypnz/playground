/**
 * 
 */
package jerry.c2c.domain;

import java.sql.Timestamp;

/**
 * @author Jerry
 *
 */
public class ItemComment
{
	private long id;
	private User maker;
	private Item destItem;
	private String content;
	private Timestamp time;
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
	 * @return the destItem
	 */
	public Item getDestItem()
	{
		return destItem;
	}
	/**
	 * @param destItem the destItem to set
	 */
	public void setDestItem(Item destItem)
	{
		this.destItem = destItem;
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
	 * @return the maker
	 */
	public User getMaker()
	{
		return maker;
	}
	/**
	 * @param maker the maker to set
	 */
	public void setMaker(User maker)
	{
		this.maker = maker;
	}
	/**
	 * @return the time
	 */
	public Timestamp getTime()
	{
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Timestamp time)
	{
		this.time = time;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0)
	{
		return ((ItemComment)arg0).getId()==this.id;
	}

}
