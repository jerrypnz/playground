/**
 * 
 */
package jerry.c2c.domain;

import java.sql.Timestamp;
import java.util.Set;
import java.util.HashSet;

/**
 * @author Jerry
 *
 */
public class Shop
{
	private long id;
	private User owner;
	private String name;
	private String description;
	private Set<Item> items = new HashSet<Item>();
	private Timestamp createTime;
	private int credit;
	private int visitTimes;
	private Category category;
	private Set<ShopComment> comments = new HashSet<ShopComment>();
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public boolean equals(Object obj)
	{
		Shop other = (Shop)obj;
		return (this.id==other.getId());
	}
	/**
	 * @return the category
	 */
	public Category getCategory()
	{
		return category;
	}
	/**
	 * @return the comments
	 */
	public Set<ShopComment> getComments()
	{
		return comments;
	}
	/**
	 * @return the createTime
	 */
	public Timestamp getCreateTime()
	{
		return createTime;
	}
	/**
	 * @return the credit
	 */
	public int getCredit()
	{
		return credit;
	}
	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}
	/**
	 * @return the id
	 */
	public long getId()
	{
		return id;
	}
	/**
	 * @return the items
	 */
	public Set<Item> getItems()
	{
		return items;
	}
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @return the owner
	 */
	public User getOwner()
	{
		return owner;
	}
	/**
	 * @return the visitTimes
	 */
	public int getVisitTimes()
	{
		return visitTimes;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category)
	{
		this.category = category;
	}
	/**
	 * @param comments the comments to set
	 */
	public void setComments(Set<ShopComment> comments)
	{
		this.comments = comments;
	}
	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(int credit)
	{
		this.credit = credit;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id)
	{
		this.id = id;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(Set<Item> items)
	{
		this.items = items;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(User owner)
	{
		this.owner = owner;
	}
	/**
	 * @param visitTimes the visitTimes to set
	 */
	public void setVisitTimes(int visitTimes)
	{
		this.visitTimes = visitTimes;
	}
}
