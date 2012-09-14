package com.jstudio.jrestoa.domain.test;

import java.util.List;

import org.junit.Test;

import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.PersonalFile;

import static org.junit.Assert.*;

public class TestPersonalFile extends DomainTestBase {
	@Test
	public void testAll() {
		Employee emp = new Employee();
		emp.setName("Jerry");
		emp.setLoginName("moonranger");
		emp.setPhoneNumber("11111");
		emp.setLoginPassword("343434");
		emp.setSex("male");
		emp.save();
		
		PersonalFile file1 = new PersonalFile();
		file1.setFileName("hello.doc");
		file1.setFilePath("dir");
		file1.setOwner(emp);
		file1.save();
		
		PersonalFile file2 = new PersonalFile();
		file2.setFileName("hello.doc");
		file2.setFilePath("dir");
		file2.setOwner(emp);
		file2.save();
		
		PersonalFile tmpFile = PersonalFile.findById(file1.getId());
		assertEquals("Not the same file",tmpFile,file1);
		
		List<PersonalFile> myfiles = emp.listPersonalFiles();
		assertTrue("File owner error",myfiles.contains(file1));
		assertTrue("File owner error",myfiles.contains(file2));

	}
}
