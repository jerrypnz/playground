package com.jstudio.jrestoa.domain;

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
@Table(name = "shared_file")
public class SharedFile extends DomainBase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "file_name")
	private String fileName;

	@Column(name = "file_path")
	private String filePath;

	@Column(name = "scope")
	private Integer scope = Constants.SHARE_FILE_GLOBAL;

	@ManyToOne
	@JoinColumn(name = "department_id")
	@XStreamOmitField
	private Department department;

	public static SharedFile findById(Long id) {
		return (SharedFile) HbmManager.get(SharedFile.class, id);
	}

	@SuppressWarnings(value = "unchecked")
	public static List<SharedFile> listGlobalFiles() {
		return HbmManager.query(SharedFile.class, "scope=?",
				new Object[] { Constants.SHARE_FILE_GLOBAL });
	}

	@SuppressWarnings(value = "unchecked")
	public static List<SharedFile> listDepartmentFiles(Department dep) {
		return HbmManager.query(SharedFile.class, "scope=? and department=?",
				new Object[] { Constants.SHARE_FILE_DEPARTMENT, dep });
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName
	 *            the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the type
	 */
	public Integer getScope() {
		return scope;
	}

	/**
	 * @param scope
	 *            the type to set
	 */
	public void setScope(Integer scope) {
		if (scope == Constants.SHARE_FILE_GLOBAL)
			setDepartment(null);
		this.scope = scope;
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
			setScope(Constants.SHARE_FILE_DEPARTMENT);
		this.department = department;
	}
}
