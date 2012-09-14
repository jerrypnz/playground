package com.jstudio.jrestoa.domain;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.jstudio.jrestoa.domain.constant.Constants;
import com.jstudio.jrestoa.hbm.HbmManager;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name = "ask_off_record")
public class AskOffRecord extends DomainBase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	@XStreamOmitField
	private Employee employee;

	@Column(name = "start_time", nullable = false)
	private Timestamp startTime;

	@Column(name = "end_time", nullable = false)
	private Timestamp endTime;

	@Column(name = "type")
	private Integer type = Constants.ASKOFF_TYPE_SICK;

	@Column(name = "reason", nullable = false)
	private String reason;

	@Column(name = "is_permitted")
	private Boolean permitted = false;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "signer_id")
	@XStreamOmitField
	private Supervisor signer;

	@Column(name = "apply_time", nullable = false)
	private Timestamp applyTime;

	@Column(name = "sign_time")
	private Timestamp signTime;

	public static AskOffRecord findById(Long id) {
		return (AskOffRecord) HbmManager.get(AskOffRecord.class, id);
	}

	/**
	 * @return the employee
	 */
	public Employee getEmployee() {
		return employee;
	}

	/**
	 * @return the endTime
	 */
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @return the signer
	 */
	public Supervisor getSigner() {
		return signer;
	}

	/**
	 * @return the startTime
	 */
	public Timestamp getStartTime() {
		return startTime;
	}

	/**
	 * @return the type
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * @return the verified
	 */
	public Boolean getVerified() {
		return permitted;
	}

	/**
	 * @param employee
	 *            the employee to set
	 */
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	/**
	 * @param endTime
	 *            the endTime to set
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param reason
	 *            the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @param signer
	 *            the signer to set
	 */
	public void setSigner(Supervisor signer) {
		this.signer = signer;
	}

	/**
	 * @param startTime
	 *            the startTime to set
	 */
	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * @param verified
	 *            the verified to set
	 */
	public void setVerified(Boolean verified) {
		this.permitted = verified;
	}

	public Timestamp getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(Timestamp applyTime) {
		this.applyTime = applyTime;
	}

	public Timestamp getSignTime() {
		return signTime;
	}

	public void setSignTime(Timestamp signTime) {
		this.signTime = signTime;
	}

}
