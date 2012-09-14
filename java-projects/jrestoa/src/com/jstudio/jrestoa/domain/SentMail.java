package com.jstudio.jrestoa.domain;

import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jstudio.jrestoa.hbm.HbmManager;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name = "mail_outbox")
public class SentMail extends DomainBase
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "receiver")
	private String receiver;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	@XStreamOmitField
	private Employee sender;

	@Column(name = "title")
	private String title;

	@Column(name = "body", columnDefinition = "text")
	@Basic(fetch = FetchType.LAZY)
	@XStreamOmitField
	private String body;

	@Column(name = "send_time")
	private Timestamp sendTime = new Timestamp(System.currentTimeMillis());

	public static SentMail findById(Long id)
	{
		return (SentMail) HbmManager.get(SentMail.class, id);
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

	/**
	 * @return the receiver
	 */
	public String getReceiver()
	{
		return receiver;
	}

	/**
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(String receiver)
	{
		this.receiver = receiver;
	}

	/**
	 * @return the sender
	 */
	public Employee getSender()
	{
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(Employee sender)
	{
		this.sender = sender;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the body
	 */
	public String getBody()
	{
		return body;
	}

	/**
	 * @param body
	 *            the body to set
	 */
	public void setBody(String body)
	{
		this.body = body;
	}

	/**
	 * @return the sendTime
	 */
	public Timestamp getSendTime()
	{
		return sendTime;
	}

	/**
	 * @param sendTime
	 *            the sendTime to set
	 */
	public void setSendTime(Timestamp sendTime)
	{
		this.sendTime = sendTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
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
		final SentMail other = (SentMail) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}
}
