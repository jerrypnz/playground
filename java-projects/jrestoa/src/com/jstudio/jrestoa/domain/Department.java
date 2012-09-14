package com.jstudio.jrestoa.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jstudio.jrestoa.hbm.HbmManager;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name="department")
public class Department extends DomainBase
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(name="name")
	private String name;

	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="supervisor_id")
	@XStreamOmitField
	private Supervisor supervisor;
	
	@OneToMany(mappedBy="department",cascade=CascadeType.ALL)
	@XStreamOmitField
	private Set<Employee> employees = new HashSet<Employee>();
	
	public static Department findById(Long id)
	{
		return (Department)HbmManager.get(Department.class, id);
	}
	
	@SuppressWarnings("unchecked")
	public static Department findByName(String name)
	{
		Department result = null;
		List temp = HbmManager.query(Department.class, "name=?",new Object[]{name});
		if(temp.size()!=0)
		{
			result = (Department)temp.get(0);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Department> listAll()
	{
		return (List<Department>)HbmManager.listAll(Department.class);
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
	 * @return the supervisor
	 */
	public Supervisor getSupervisor()
	{
		return supervisor;
	}

	/**
	 * @param supervisor
	 *            the supervisor to set
	 */
	public void setSupervisor(Supervisor supervisor)
	{
		this.supervisor = supervisor;
	}

	/**
	 * @return the employees
	 */
	public Set<Employee> getEmployees()
	{
		return employees;
	}

	/**
	 * @param employees the employees to set
	 */
	public void setEmployees(Set<Employee> employees)
	{
		this.employees = employees;
	}

}
