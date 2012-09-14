package com.jstudio.jrestoa.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jstudio.jrestoa.domain.constant.Constants;
import com.jstudio.jrestoa.hbm.HbmManager;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name = "news")
public class News extends DomainBase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title")
	private String title;

	@Column(name = "detail")
	private String detail;

	@Column(name = "time")
	private Timestamp time = new Timestamp(System.currentTimeMillis());

	@Column(name = "scope")
	private Integer scope = Constants.NEWS_SCOPE_GLOBAL;

	@ManyToOne
	@JoinColumn(name = "department_id")
	@XStreamOmitField
	private Department department;

	public static News findById(Long id) {
		return (News) HbmManager.get(News.class, id);
	}

	@SuppressWarnings("unchecked")
	public static List<News> listGlobalNews() {
		return HbmManager.query(News.class, "scope=?",
				new Object[] { Constants.NEWS_SCOPE_GLOBAL });
	}

	@SuppressWarnings("unchecked")
	public static List<News> listDepartmentNews(Department dep) {
		return HbmManager.query(News.class, "scope=? and department=?",
				new Object[] { Constants.NEWS_SCOPE_DEPARTMENT, dep });
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the detail
	 */
	public String getDetail() {
		return detail;
	}

	/**
	 * @param detail
	 *            the detail to set
	 */
	public void setDetail(String detail) {
		this.detail = detail;
	}

	/**
	 * @return the time
	 */
	public Timestamp getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(Timestamp time) {
		this.time = time;
	}

	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department
	 *            the department to set
	 */
	public void setDepartment(Department department) {
		if (department != null)
			setScope(Constants.NEWS_SCOPE_DEPARTMENT);
		this.department = department;
	}

	/**
	 * @return the scope
	 */
	public Integer getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the scope to set
	 */
	public void setScope(Integer scope) {
		this.scope = scope;
		if (scope == Constants.NEWS_SCOPE_GLOBAL) {
			setDepartment(null);
		}
	}
}
