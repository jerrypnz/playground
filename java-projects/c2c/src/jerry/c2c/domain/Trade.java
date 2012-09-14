/**
 * 
 */
package jerry.c2c.domain;

import java.sql.Timestamp;

/**
 * @author Jerry
 *
 */
public class Trade
{
	private long id;
	private Item item;
	private User buyer;
	private Timestamp createTime;
	private Timestamp endTime;
	private String description;
	private int price;
	/**
	 * @return the price
	 */
	public int getPrice()
	{
		return price;
	}
	/**
	 * @param price the price to set
	 */
	public void setPrice(int price)
	{
		this.price = price;
	}
	/**
	 * 
	 */
	public Trade()
	{
		super();
	}
	/**
	 * @param item
	 */
	public Trade(Item item)
	{
		super();
		this.item = item;
	}
	/**
	 * @return the buyer
	 */
	public User getBuyer()
	{
		return buyer;
	}
	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime()
	{
		return createTime;
	}
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * @return the endTime
	 */
	public Timestamp getEndTime()
	{
		return endTime;
	}
	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @return the item
	 */
	public Item getItem()
	{
		return item;
	}
	/**
	 * @param buyer the buyer to set
	 */
	public void setBuyer(User buyer)
	{
		this.buyer = buyer;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Timestamp endTime)
	{
		this.endTime = endTime;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	/**
	 * @param item the item to set
	 */
	public void setItem(Item item)
	{
		this.item = item;
	}
	
}
