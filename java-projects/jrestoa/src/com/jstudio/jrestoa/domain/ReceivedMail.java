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
import com.jstudio.jrestoa.rss.RSSChanel;
import com.jstudio.jrestoa.rss.RSSItem;
import com.jstudio.jrestoa.util.DateUtil;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name = "mail_inbox")
public class ReceivedMail extends DomainBase
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "receiver_id")
	@XStreamOmitField
	private Employee receiver;

	@Column(name = "sender")
	private String sender;

	@Column(name = "title")
	private String title;

	@Column(name = "body", columnDefinition = "text")
	@Basic(fetch = FetchType.LAZY)
	@XStreamOmitField
	private String body;

	@Column(name = "send_time")
	private Timestamp sendTime = new Timestamp(System.currentTimeMillis());

	@Column(name = "is_read")
	private boolean read = false;

	public static ReceivedMail findById(Long id)
	{
		return (ReceivedMail) HbmManager.get(ReceivedMail.class, id);
	}

	/**
	 * @return the read
	 */
	public boolean isRead()
	{
		return read;
	}

	/**
	 * @param read
	 *            the read to set
	 */
	public void setRead(boolean read)
	{
		this.read = read;
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
	public Employee getReceiver()
	{
		return receiver;
	}

	/**
	 * @param receiver
	 *            the receiver to set
	 */
	public void setReceiver(Employee receiver)
	{
		this.receiver = receiver;
	}

	/**
	 * @return the sender
	 */
	public String getSender()
	{
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(String sender)
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
		final ReceivedMail other = (ReceivedMail) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

	public RSSItem asRSSItem()
	{
		RSSItem result = new RSSItem();
		result.setPubDate(DateUtil.toGMTString(this.getSendTime()));
		result.setTitle(this.title);
		result.setDescription(this.body);
		result.setGuid("received_mail_" + this.id);
		return result;
	}
	
	public void addToChannel(RSSChanel chanel)
	{
		chanel.getItems().add(this.asRSSItem());
	}
}
