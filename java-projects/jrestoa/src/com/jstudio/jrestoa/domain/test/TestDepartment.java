package com.jstudio.jrestoa.domain.test;

import java.util.List;

import org.junit.Test;

import com.jstudio.jrestoa.domain.Department;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.Supervisor;

import static org.junit.Assert.*;

public class TestDepartment extends DomainTestBase {

	@Test
	public void testSaveGet() throws Exception {
		Department dep = new Department();
		dep.setName("Server");
		dep.save();
		
		Department dep2 = Department.findById(dep.getId());
		assertEquals(dep, dep2);
	}

	@Test
	public void testGetAll() throws Exception {
		Department dep = new Department();
		Department dep2 = new Department();
		dep.setName("Server");
		dep2.setName("Com Server");
		dep.save();
		dep2.save();

		List<Department> all = Department.listAll();
		assertTrue("All department list does not contain dep1", all
				.contains(dep));
		assertTrue("All department list does not contain dep2", all
				.contains(dep2));
	}
	
	@Test
	public void testDepEmployee() throws Exception {
		Department dep = new Department();
		dep.setName("Server");
		Employee emp1 = new Employee();
		emp1.setName("Jerry");
		emp1.setLoginName("jerry");
		Supervisor sup1 = new Supervisor();
		sup1.setName("Jim");
		sup1.setLoginName("jim");
		
		emp1.linkToDepartment(dep);
		sup1.linkToDepartment(dep);
		dep.save();
		emp1.save();
		sup1.save();
		
		Department dep2 = Department.findById(dep.getId());
		assertTrue(dep2.getEmployees().contains(emp1));
		assertTrue(dep2.getEmployees().contains(sup1));
		
	}

}
