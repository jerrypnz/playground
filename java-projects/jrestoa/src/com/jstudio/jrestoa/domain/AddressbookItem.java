package com.jstudio.jrestoa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jstudio.jrestoa.hbm.HbmManager;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name="addressbook")
public class AddressbookItem extends DomainBase
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="name")
	private String name;

	@Column(name="mobile_phone")
	private String mobilePhone;

	@Column(name="home_phone")
	private String homePhone;

	@Column(name="email")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="owner_id")
	@XStreamOmitField
	private Employee owner;
	
	public static AddressbookItem findById(Long id)
	{
		return (AddressbookItem)HbmManager.get(AddressbookItem.class, id);
	}

	/**
	 * @return the owner
	 */
	public Employee getOwner()
	{
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(Employee owner)
	{
		this.owner = owner;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the mobilePhone
	 */
	public String getMobilePhone()
	{
		return mobilePhone;
	}

	/**
	 * @param mobilePhone
	 *            the mobilePhone to set
	 */
	public void setMobilePhone(String mobilePhone)
	{
		this.mobilePhone = mobilePhone;
	}

	/**
	 * @return the homePhone
	 */
	public String getHomePhone()
	{
		return homePhone;
	}

	/**
	 * @param homePhone
	 *            the homePhone to set
	 */
	public void setHomePhone(String homePhone)
	{
		this.homePhone = homePhone;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((homePhone == null) ? 0 : homePhone.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((mobilePhone == null) ? 0 : mobilePhone.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final AddressbookItem other = (AddressbookItem) obj;
		if (email == null)
		{
			if (other.email != null)
				return false;
		}
		else if (!email.equals(other.email))
			return false;
		if (homePhone == null)
		{
			if (other.homePhone != null)
				return false;
		}
		else if (!homePhone.equals(other.homePhone))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (mobilePhone == null)
		{
			if (other.mobilePhone != null)
				return false;
		}
		else if (!mobilePhone.equals(other.mobilePhone))
			return false;
		if (name == null)
		{
			if (other.name != null)
				return false;
		}
		else if (!name.equals(other.name))
			return false;
		return true;
	}

}
