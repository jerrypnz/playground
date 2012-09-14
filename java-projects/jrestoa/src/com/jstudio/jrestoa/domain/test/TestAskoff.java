package com.jstudio.jrestoa.domain.test;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import com.jstudio.jrestoa.domain.AskOffRecord;
import com.jstudio.jrestoa.domain.Department;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.ReceivedMail;
import com.jstudio.jrestoa.domain.Supervisor;
import com.jstudio.jrestoa.domain.constant.Constants;

import static org.junit.Assert.*;

public class TestAskoff extends DomainTestBase {

	@Test
	public void testSaveGet() {
		Employee emp = new Employee();
		emp.setName("Jerry");
		emp.setLoginName("moonranger");
		emp.setPhoneNumber("11111");
		emp.setLoginPassword("343434");
		emp.setSex("male");
		emp.save();

		AskOffRecord askoff1 = new AskOffRecord();
		askoff1.setEmployee(emp);
		askoff1.setApplyTime(new Timestamp(System.currentTimeMillis()));
		askoff1.setReason("I have to buy PSP");
		askoff1.setStartTime(Timestamp.valueOf("2008-05-19 09:30:15"));
		askoff1.setEndTime(Timestamp.valueOf("2008-05-10 09:30:15"));
		askoff1.setType(Constants.ASKOFF_TYPE_AFFAIR);
		askoff1.save();

		AskOffRecord askoff2 = AskOffRecord.findById(askoff1.getId());

		assertEquals("The two ask off record are not equal.", askoff1, askoff2);
	}

	@Test
	public void testAskoff() throws Exception {
		Department dep = new Department();
		dep.setName("Server");

		Supervisor sup = new Supervisor();
		sup.setName("MM");
		sup.setLoginName("Nandy");
		sup.setSex("female");
		sup.setDepartment(dep);
		dep.setSupervisor(sup);

		dep.save();
		sup.save();

		Employee emp1 = new Employee();
		emp1.setName("Jerry");
		emp1.setLoginName("moonranger");
		emp1.setPhoneNumber("11111");
		emp1.setLoginPassword("343434");
		emp1.setSex("male");
		emp1.setDepartment(dep);
		emp1.save();

		Employee emp2 = new Employee();
		emp2.setName("Foo");
		emp2.setLoginName("foo");
		emp2.setPhoneNumber("11111");
		emp2.setLoginPassword("343434");
		emp2.setSex("male");
		emp2.setDepartment(dep);
		emp2.save();

		AskOffRecord askoff1 = new AskOffRecord();
		askoff1.setEmployee(emp1);
		askoff1.setApplyTime(new Timestamp(System.currentTimeMillis()));
		askoff1.setReason("I have to buy PSP");
		askoff1.setStartTime(Timestamp.valueOf("2008-05-19 09:30:15"));
		askoff1.setEndTime(Timestamp.valueOf("2008-05-10 09:30:15"));
		askoff1.setType(Constants.ASKOFF_TYPE_AFFAIR);
		askoff1.save();

		AskOffRecord askoff2 = new AskOffRecord();
		askoff2.setEmployee(emp2);
		askoff2.setApplyTime(new Timestamp(System.currentTimeMillis()));
		askoff2.setReason("I have to get married");
		askoff2.setStartTime(Timestamp.valueOf("2008-05-29 09:30:15"));
		askoff2.setEndTime(Timestamp.valueOf("2008-06-10 19:30:15"));
		askoff2.setType(Constants.ASKOFF_TYPE_AFFAIR);
		askoff2.save();

		List<AskOffRecord> ofEmp1 = emp1.listAskOffRecords();
		assertTrue("Askoff records are not correctly saved.", ofEmp1
				.contains(askoff1));
		List<AskOffRecord> ofEmp2 = emp2.listAskOffRecords();
		assertTrue("Askoff records are not correctly saved.", ofEmp2
				.contains(askoff2));

		List<AskOffRecord> unverified = sup.listUnsignedAskOff();
		System.out.println("Unverified records:" + unverified.size());
		assertTrue("Askoff records are not correctly saved.", unverified
				.contains(askoff1));
		assertTrue("Askoff records are not correctly saved.", unverified
				.contains(askoff2));

		sup.permitAskOff(askoff1);
		assertTrue("Askoff record is not correctly verified.", askoff1
				.getVerified());
		assertNotNull(askoff1.getSignTime());
		assertEquals("Signer is not correct",sup,askoff1.getSigner());
		assertTrue("Mail is not correctly sent",emp1.receiveMail().size()>0);
		ReceivedMail mail = emp1.receiveMail().get(0);
		System.out.println("Sender:" + mail.getSender());
		System.out.println("Send time:" + mail.getSendTime());
		System.out.println("Mail Title:" + mail.getTitle());
		System.out.println("Mail Body:");
		System.out.println(mail.getBody());

	}
}
