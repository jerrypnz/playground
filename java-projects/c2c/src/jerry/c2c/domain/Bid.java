/**
 * 
 */
package jerry.c2c.domain;

import java.sql.Timestamp;

/**
 * @author Jerry
 *
 */
public class Bid
{
	private long id;
	private Item item;
	private User maker;
	private int currentPrice;
	private String description;
	private Timestamp time;
	/**
	 * @return the currentPrice
	 */
	public int getCurrentPrice()
	{
		return currentPrice;
	}
	/**
	 * @param currentPrice the currentPrice to set
	 */
	public void setCurrentPrice(int currentPrice)
	{
		this.currentPrice = currentPrice;
	}
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	/**
	 * @return the item
	 */
	public Item getItem()
	{
		return item;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Item item)
	{
		this.item = item;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0)
	{
		return ((Bid)arg0).getId() == this.id;
	}
	
	
}
