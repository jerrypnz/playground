package com.jstudio.jrestoa.domain;

import java.sql.Timestamp;
import java.util.Calendar;

import com.jstudio.jrestoa.rss.RSSChanel;
import com.jstudio.jrestoa.rss.RSSItem;
import com.jstudio.jrestoa.util.DateUtil;

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
@Table(name = "appointment")
public class Appointment extends DomainBase
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "detail")
	private String detail;

	@Column(name = "start_time")
	private Timestamp startTime;

	@Column(name = "complete_time")
	private Timestamp completeTime;

	@Column(name = "finished")
	private Boolean finished = false;

	@ManyToOne
	@JoinColumn(name = "owner_id")
	@XStreamOmitField
	private Employee owner;

	public static Appointment findById(Long id)
	{
		return (Appointment) HbmManager.get(Appointment.class, id);
	}

	public void finish()
	{
		this.setFinished(true);
		this.update();
	}

	/**
	 * @return the owner
	 */
	public Employee getOwner()
	{
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(Employee owner)
	{
		this.owner = owner;
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
	 * @return the detail
	 */
	public String getDetail()
	{
		return detail;
	}

	/**
	 * @param detail
	 *            the detail to set
	 */
	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	/**
	 * @return the startTime
	 */
	public Timestamp getStartTime()
	{
		return startTime;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Timestamp startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * @return the completeTime
	 */
	public Timestamp getCompleteTime()
	{
		return completeTime;
	}

	/**
	 * @param completeTime
	 *            the completeTime to set
	 */
	public void setCompleteTime(Timestamp completeTime)
	{
		this.completeTime = completeTime;
	}

	/**
	 * @return the finished
	 */
	public Boolean getFinished()
	{
		return finished;
	}

	/**
	 * @param finished
	 *            the finished to set
	 */
	public void setFinished(Boolean finished)
	{
		this.finished = finished;
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
		result = prime * result
				+ ((completeTime == null) ? 0 : completeTime.hashCode());
		result = prime * result + ((detail == null) ? 0 : detail.hashCode());
		result = prime * result
				+ ((finished == null) ? 0 : finished.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		final Appointment other = (Appointment) obj;
		if (completeTime == null)
		{
			if (other.completeTime != null)
				return false;
		}
		else if (!completeTime.equals(other.completeTime))
			return false;
		if (detail == null)
		{
			if (other.detail != null)
				return false;
		}
		else if (!detail.equals(other.detail))
			return false;
		if (finished == null)
		{
			if (other.finished != null)
				return false;
		}
		else if (!finished.equals(other.finished))
			return false;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (startTime == null)
		{
			if (other.startTime != null)
				return false;
		}
		else if (!startTime.equals(other.startTime))
			return false;
		if (title == null)
		{
			if (other.title != null)
				return false;
		}
		else if (!title.equals(other.title))
			return false;
		return true;
	}

	public RSSItem asRSSItem()
	{
		RSSItem result = new RSSItem();
		Timestamp temp = this.getStartTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(temp.getTime());
		cal.add(Calendar.HOUR_OF_DAY, -4);
		result.setPubDate(DateUtil.toGMTString(new Timestamp(cal
				.getTimeInMillis())));

		result.setTitle(this.title);
		result.setDescription(this.detail);
		result.setGuid("appointment_" + this.id);
		return result;
	}
	
	public void addToChannel(RSSChanel chanel)
	{
		chanel.getItems().add(this.asRSSItem());
	}
}
