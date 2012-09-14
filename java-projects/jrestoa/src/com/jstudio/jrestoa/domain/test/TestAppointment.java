package com.jstudio.jrestoa.domain.test;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import com.jstudio.jrestoa.domain.Appointment;
import com.jstudio.jrestoa.domain.Employee;

import static org.junit.Assert.*;

public class TestAppointment extends DomainTestBase {

	@Test
	public void testSaveGet() {
		Appointment app = new Appointment();
		app.setTitle("Hello");
		app.setDetail("Say hello to the world");
		Timestamp startTime = Timestamp.valueOf("2008-05-10 18:20:10");
		Timestamp endTime = Timestamp.valueOf("2008-05-10 19:20:10");
		app.setStartTime(startTime);
		app.setCompleteTime(endTime);
		app.save();

		Appointment app2 = Appointment.findById(app.getId());
		assertEquals("The two appoinments should be equal!", app, app2);
	}

	@Test
	public void testFinish() {
		Employee emp = new Employee();
		emp.setName("Jerry");
		emp.setLoginName("moonranger");
		emp.setPhoneNumber("11111");
		emp.setLoginPassword("343434");
		emp.setSex("male");
		emp.save();

		Appointment app = new Appointment();
		app.setTitle("Hello1");
		app.setDetail("Say hello to the world1");
		Timestamp startTime = Timestamp.valueOf("2008-05-10 12:20:10");
		Timestamp endTime = Timestamp.valueOf("2008-05-10 14:20:10");
		app.setStartTime(startTime);
		app.setCompleteTime(endTime);
		app.setOwner(emp);
		app.save();

		Appointment app2 = new Appointment();
		app2.setTitle("Hello");
		app2.setDetail("Say hello to the world");
		Timestamp startTime2 = Timestamp.valueOf("2008-05-10 15:20:10");
		Timestamp endTime2 = Timestamp.valueOf("2008-05-10 18:20:10");
		app2.setStartTime(startTime2);
		app2.setCompleteTime(endTime2);
		app2.setFinished(true);
		app2.setOwner(emp);
		app2.save();

		List<Appointment> all = emp.listAllAppointments();
		List<Appointment> finished = emp.listFinishedAppointments();
		assertTrue(all.contains(app));
		assertTrue(all.contains(app2));

		assertTrue(finished.contains(app2));
		assertFalse(finished.contains(app));
	}

	@Test
	public void testNotice() {

		Employee emp = new Employee();
		emp.setName("Jerry");
		emp.setLoginName("moonranger");
		emp.setPhoneNumber("11111");
		emp.setLoginPassword("343434");
		emp.setSex("male");
		emp.save();

		Appointment app = new Appointment();
		app.setTitle("Hello1");
		app.setDetail("Say hello to the world1");
		Timestamp startTime = Timestamp.valueOf("2008-05-10 12:20:10");
		Timestamp endTime = Timestamp.valueOf("2008-05-10 14:20:10");
		app.setStartTime(startTime);
		app.setCompleteTime(endTime);
		app.setOwner(emp);
		app.save();

		Appointment app2 = new Appointment();
		app2.setTitle("Hello");
		app2.setDetail("Say hello to the world");
		Timestamp startTime2 = Timestamp.valueOf("2008-05-10 15:20:10");
		Timestamp endTime2 = Timestamp.valueOf("2008-05-10 18:20:10");
		app2.setStartTime(startTime2);
		app2.setCompleteTime(endTime2);
		app2.setOwner(emp);
		app2.save();

		List<Appointment> list1 = emp.listNoticeAppointments(Timestamp
				.valueOf("2008-05-10 08:21:10"));
		assertTrue("Not correctly noticed.", list1.contains(app));
		assertFalse("Not correctly noticed.", list1.contains(app2));
		
		List<Appointment> list2 = emp.listNoticeAppointments(Timestamp
				.valueOf("2008-05-10 14:30:10"));
		assertTrue("Not correctly noticed.", list2.contains(app));
		assertTrue("Not correctly noticed.", list2.contains(app2));
		
		List<Appointment> list3 = emp.listNoticeAppointments(Timestamp
				.valueOf("2008-05-09 14:30:10"));
		assertFalse("Not correctly noticed.", list3.contains(app));
		assertFalse("Not correctly noticed.", list3.contains(app2));

	}
	
	@Test
	public void testToday() {

		Employee emp = new Employee();
		emp.setName("Jerry");
		emp.setLoginName("moonranger");
		emp.setPhoneNumber("11111");
		emp.setLoginPassword("343434");
		emp.setSex("male");
		emp.save();

		Appointment app = new Appointment();
		app.setTitle("Hello1");
		app.setDetail("Say hello to the world1");
		Timestamp startTime = Timestamp.valueOf("2008-05-10 12:20:10");
		Timestamp endTime = Timestamp.valueOf("2008-05-10 14:20:10");
		app.setStartTime(startTime);
		app.setCompleteTime(endTime);
		app.setOwner(emp);
		app.save();

		Appointment app2 = new Appointment();
		app2.setTitle("Hello");
		app2.setDetail("Say hello to the world");
		Timestamp startTime2 = Timestamp.valueOf("2008-05-10 15:20:10");
		Timestamp endTime2 = Timestamp.valueOf("2008-05-10 18:20:10");
		app2.setStartTime(startTime2);
		app2.setCompleteTime(endTime2);
		app2.setOwner(emp);
		app2.save();
		
		Appointment app3 = new Appointment();
		app3.setTitle("Hello");
		app3.setDetail("Say hello to the world");
		Timestamp startTime3 = Timestamp.valueOf("2008-05-11 15:20:10");
		Timestamp endTime3 = Timestamp.valueOf("2008-05-11 18:20:10");
		app3.setStartTime(startTime3);
		app3.setCompleteTime(endTime3);
		app3.setOwner(emp);
		app3.save();

		List<Appointment> list1 = emp.listTodayAppointments("2008-05-10");
		assertTrue("Not correctly noticed.", list1.contains(app));
		assertTrue("Not correctly noticed.", list1.contains(app2));
		assertFalse("Not correctly noticed.", list1.contains(app3));
		
		List<Appointment> list2 = emp.listTodayAppointments("2008-05-11");
		assertFalse("Not correctly noticed.", list2.contains(app));
		assertFalse("Not correctly noticed.", list2.contains(app2));
		assertTrue("Not correctly noticed.", list2.contains(app3));
		
		List<Appointment> list3 = emp.listTodayAppointments("2018-05-11");
		assertFalse("Not correctly noticed.", list3.contains(app));
		assertFalse("Not correctly noticed.", list3.contains(app2));
		assertFalse("Not correctly noticed.", list3.contains(app3));

	}
}
