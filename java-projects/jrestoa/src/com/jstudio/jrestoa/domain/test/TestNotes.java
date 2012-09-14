package com.jstudio.jrestoa.domain.test;

import java.util.List;

import org.junit.Test;

import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.Note;

import static org.junit.Assert.*;

public class TestNotes extends DomainTestBase {
	
	@Test
	public void testSaveGet() {
		Employee emp = new Employee();
		emp.setName("Jerry");
		emp.setLoginName("moonranger");
		emp.setPhoneNumber("11111");
		emp.setLoginPassword("343434");
		emp.setSex("male");
		emp.save();
		
		Note note1 = new Note();
		note1.setContent("blah,blah,blah,blah...");
		note1.setOwner(emp);
		note1.save();
		
		Note note2 = Note.findById(note1.getId());
		
		assertEquals("Notes are not correctly saved.",note1,note2);
	}
	
	@Test
	public void testDesktopNotes() {
		Employee emp = new Employee();
		emp.setName("Jerry");
		emp.setLoginName("moonranger");
		emp.setPhoneNumber("11111");
		emp.setLoginPassword("343434");
		emp.setSex("male");
		emp.save();
		
		Note note1 = new Note();
		note1.setContent("blah,blah,blah,blah...");
		note1.setOwner(emp);
		note1.save();
		
		Note note2 = new Note();
		note2.setContent("Please show me on your desktop!");
		note2.setOwner(emp);
		note2.setShowOnDesktop(true);
		note2.save();
		
		List<Note> all = emp.listAllNotes();
		List<Note> desktops = emp.listDesktopNotes();
		
		assertTrue("Notes not correctly saved.",all.contains(note1));
		assertTrue("Notes not correctly saved.",all.contains(note2));
		
		assertFalse("Desktop note error",desktops.contains(note1));
		assertTrue("Desktop note error",desktops.contains(note2));

	}
}
