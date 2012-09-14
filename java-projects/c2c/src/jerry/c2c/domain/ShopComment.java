package jerry.c2c.domain;

import java.sql.Timestamp;

public class ShopComment
{
	private long id;
	private User maker;
	private Shop destShop;
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
	 * @return the destShop
	 */
	public Shop getDestShop()
	{
		return destShop;
	}
	/**
	 * @param destShop the destShop to set
	 */
	public void setDestShop(Shop destShop)
	{
		this.destShop = destShop;
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + (int) (id ^ (id >>> 32));
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
		if (getClass() != obj.getClass())
			return false;
		final ShopComment other = (ShopComment) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
