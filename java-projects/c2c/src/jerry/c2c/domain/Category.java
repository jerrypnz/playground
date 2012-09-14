/**
 * 
 */
package jerry.c2c.domain;

/**
 * @author Jerry
 *
 */
public class Category
{
	private long id;
	private String name;
	private String description;
	private Category parent;	
	
	/**
	 * @param name
	 * @param description
	 * @param parent
	 */
	public Category(String name, String description, Category parent)
	{
		this.name = name;
		this.description = description;
		this.parent = parent;
	}
	
	
	/**
	 * @param name
	 */
	public Category(String name)
	{
		this(name,"",null);
	}
	
	public Category(){}

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
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}
	/**
	 * @return the parent
	 */
	public Category getParent()
	{
		return parent;
	}
	/**
	 * @param parent the parent to set
	 */
	public void setParent(Category parent)
	{
		this.parent = parent;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object arg0)
	{
		return ((Category)arg0).getId()==this.id;
	}
}
