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
@Table(name="note")
public class Note extends DomainBase
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="content")
	private String content;

	@Column(name="show_on_desktop")
	private Boolean showOnDesktop = false;
	
	@ManyToOne
	@JoinColumn(name="owner_id",nullable=false)
	@XStreamOmitField
	private Employee owner;

	public static Note findById(Long id) {
		return (Note)HbmManager.get(Note.class, id);
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
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @return the showOnDesktop
	 */
	public Boolean getShowOnDesktop()
	{
		return showOnDesktop;
	}

	/**
	 * @param showOnDesktop
	 *            the showOnDesktop to set
	 */
	public void setShowOnDesktop(Boolean showOnDesktop)
	{
		this.showOnDesktop = showOnDesktop;
	}
}
