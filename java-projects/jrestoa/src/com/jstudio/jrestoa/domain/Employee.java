package com.jstudio.jrestoa.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jstudio.jrestoa.domain.exception.BusinessException;
import com.jstudio.jrestoa.hbm.HbmManager;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

@Entity
@Table(name = "employee")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "is_supervisor", discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("0")
public class Employee extends DomainBase {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "login_name", unique = true)
	private String loginName;

	@Column(name = "login_password")
	private String loginPassword;

	@Column(name = "name")
	private String name;

	@Column(name = "sex")
	private String sex;

	@Column(name = "birth_day")
	private Date birthDay;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "email")
	private String email;

	@Column(name = "address")
	private String address;

	@ManyToOne
	@JoinColumn(name = "department_id")
	@XStreamOmitField
	private Department department;

	public static Employee findById(Long id){
		return (Employee) HbmManager.get(Employee.class, id);
	}

	@SuppressWarnings("unchecked")
	public static Employee findByLoginName(String name) {
		Employee result = null;
		List employees = HbmManager.query(Employee.class, "loginName=?",
				new Object[] { name });
		if (employees.size() != 0)
			result = (Employee) employees.get(0);
		return result;
	}

	public void sendMail(final String receiverName, final Mail mail)
			throws BusinessException {
		Employee receiver = findByLoginName(receiverName);
		if (receiver == null)
			throw new BusinessException("Receiver does not exists");
		SentMail sent = new SentMail();
		ReceivedMail rec = new ReceivedMail();
		sent.setTitle(mail.getTitle());
		rec.setTitle(mail.getTitle());
		sent.setBody(mail.getBody());
		rec.setBody(mail.getBody());
		sent.setSender(this);
		sent.setReceiver(receiverName);
		rec.setReceiver(receiver);
		rec.setSender(this.getLoginName());
		sent.save();
		rec.save();
	}

	@SuppressWarnings("unchecked")
	public List<ReceivedMail> receiveMail() {
		return HbmManager.query(ReceivedMail.class, "receiver=?",
				new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<SentMail> checkSentMail() {
		return HbmManager.query(SentMail.class, "sender=?",
				new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<AddressbookItem> listAddressbook() {
		return HbmManager.query(AddressbookItem.class, "owner=?",
				new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<Appointment> listAllAppointments() {
		return HbmManager.query(Appointment.class, "owner=?",
				new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<Appointment> listFinishedAppointments() {
		return HbmManager.query(Appointment.class, "owner=? and finished=true",
				new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<Appointment> listNoticeAppointments(Timestamp currentTime) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(currentTime.getTime());
		cal.add(Calendar.HOUR_OF_DAY, 4);
		currentTime = new Timestamp(cal.getTimeInMillis());
		return HbmManager.query(Appointment.class,
				"owner=? and startTime<=? and finished=false", new Object[] {
						this, currentTime });
	}
	
	@SuppressWarnings("unchecked")
	public List<Appointment> listTodayAppointments(String today) {
		Timestamp temp = Timestamp.valueOf(today + " 00:00:00");
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(temp.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		Timestamp min = new Timestamp(cal.getTimeInMillis());
		cal.add(Calendar.DAY_OF_YEAR, 1);
		Timestamp max = new Timestamp(cal.getTimeInMillis());
		return HbmManager.query(Appointment.class,
				"owner=? and startTime>=? and startTime<=? and finished=false", new Object[] {
						this, min,max });
	}

	@SuppressWarnings("unchecked")
	public List<ToDo> listAllToDos() {
		return HbmManager.query(ToDo.class, "owner=?",
				new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<ToDo> listFinishedToDos() {
		return HbmManager.query(ToDo.class, "owner=? and finished=true",
				new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<ToDo> listNoticeToDos(Timestamp currentTime) {
		return HbmManager.query(ToDo.class,
				"owner=? and alarmTime<=? and finished=false", new Object[] {
						this, currentTime });
	}

	@SuppressWarnings("unchecked")
	public List<AskOffRecord> listAskOffRecords() {
		return HbmManager.query(AskOffRecord.class, "employee=?",
				new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<PersonalFile> listPersonalFiles() {
		return HbmManager.query(PersonalFile.class, "owner=?",
				new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<Note> listAllNotes() {
		return HbmManager.query(Note.class, "owner=?", new Object[] { this });
	}

	@SuppressWarnings("unchecked")
	public List<Note> listDesktopNotes() {
		return HbmManager.query(Note.class, "owner=? and showOnDesktop=true",
				new Object[] { this });
	}

	public void linkToDepartment(Department dep) {
		if (dep != null) {
			dep.getEmployees().add(this);
			this.setDepartment(dep);
		}
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
		this.department = department;
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
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName
	 *            the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the loginPassword
	 */
	public String getLoginPassword() {
		return loginPassword;
	}

	/**
	 * @param loginPassword
	 *            the loginPassword to set
	 */
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the sex
	 */
	public String getSex() {
		return sex;
	}

	/**
	 * @param sex
	 *            the sex to set
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}

	/**
	 * @return the birthDay
	 */
	public Date getBirthDay() {
		return birthDay;
	}

	/**
	 * @param birthDay
	 *            the birthDay to set
	 */
	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber
	 *            the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((loginName == null) ? 0 : loginName.hashCode());
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
		final Employee other = (Employee) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		if (loginName == null)
		{
			if (other.loginName != null)
				return false;
		}
		else if (!loginName.equals(other.loginName))
			return false;
		return true;
	}

}
