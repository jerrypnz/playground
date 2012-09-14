package com.jstudio.jrestoa.domain.test;

import org.junit.Test;
import static org.junit.Assert.*;

import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.Supervisor;

public class TestEmployee extends DomainTestBase
{
	
	@Test
	public void testSaveGet() throws Exception
	{
		Employee employee = new Employee();
		employee.setName("Jerry");
		employee.setLoginName("moonranger");
		employee.setPhoneNumber("11111");
		employee.setLoginPassword("343434");
		employee.setSex("male");
		
		Supervisor sup = new Supervisor();
		sup.setName("MM");
		sup.setLoginName("Nandy");
		sup.setSex("female");
		
		employee.save();
		sup.save();
		
		Employee emp2 = Employee.findById(employee.getId());
		Employee emp3 = Employee.findByLoginName("moonranger");
		assertEquals(employee,emp2);
		assertEquals(employee,emp3);
		assertNull(Employee.findByLoginName("sdfjj"));	
		
		Supervisor sup2 = (Supervisor)Employee.findById(sup.getId());
		assertEquals(sup,sup2);
	}

}
