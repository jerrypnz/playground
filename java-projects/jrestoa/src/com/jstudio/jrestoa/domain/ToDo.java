package com.jstudio.jrestoa.domain;

import java.sql.Timestamp;

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
@Table(name="todos")
public class ToDo extends DomainBase
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="todo",columnDefinition="text")
	private String todo;
	
	@Column(name="alarm_time")
	private Timestamp alarmTime;
	
	@Column(name="finished")
	private Boolean finished = false;
	
	@ManyToOne
	@JoinColumn(name="owner_id",nullable=false)
	@XStreamOmitField
	private Employee owner;
	
	public static ToDo findById(Long id) {
		return (ToDo)HbmManager.get(ToDo.class, id);
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
	 * @param id the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return the todo
	 */
	public String getTodo()
	{
		return todo;
	}

	/**
	 * @param todo the todo to set
	 */
	public void setTodo(String todo)
	{
		this.todo = todo;
	}

	/**
	 * @return the alarmTime
	 */
	public Timestamp getAlarmTime()
	{
		return alarmTime;
	}

	/**
	 * @param alarmTime the alarmTime to set
	 */
	public void setAlarmTime(Timestamp alarmTime)
	{
		this.alarmTime = alarmTime;
	}

	/**
	 * @return the finished
	 */
	public Boolean getFinished()
	{
		return finished;
	}

	/**
	 * @param finished the finished to set
	 */
	public void setFinished(Boolean finished)
	{
		this.finished = finished;
	}
}
