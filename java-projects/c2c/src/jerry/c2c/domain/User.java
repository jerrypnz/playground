/**
 * 
 */
package jerry.c2c.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Jerry
 */
public class User
{
	private long id;
	private String name;
	private String nickName;
	private String password;
	private String email;
	private String sex;
	private String address;
	private Set<Shop> ownedShops = new HashSet<Shop>();
	private int credit;
	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}
	/**
	 * @return the credit
	 */
	public int getCredit()
	{
		return credit;
	}
	/**
	 * @param credit the credit to set
	 */
	public void setCredit(int credit)
	{
		this.credit = credit;
	}
	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
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
	 * @return the nickName
	 */
	public String getNickName()
	{
		return nickName;
	}
	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName)
	{
		this.nickName = nickName;
	}
	/**
	 * @return the ownedShops
	 */
	public Set<Shop> getOwnedShops()
	{
		return ownedShops;
	}
	/**
	 * @param ownedShops the ownedShops to set
	 */
	public void setOwnedShops(Set<Shop> ownedShop)
	{
		this.ownedShops = ownedShop;
	}
	/**
	 * @return the password
	 */
	public String getPassword()
	{
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password)
	{
		this.password = password;
	}
	/**
	 * @return the sex
	 */
	public String getSex()
	{
		return sex;
	}
	/**
	 * @param sex the sex to set
	 */
	public void setSex(String sex)
	{
		this.sex = sex;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		final User other = (User) obj;
		if(this.id==other.id && this.name.equals(other.name))
			return true;
		else
			return false;
			
	}
}
