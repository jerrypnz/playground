package com.jstudio.jrestoa.domain.test;

import java.util.List;

import org.junit.Test;

import com.jstudio.jrestoa.domain.AddressbookItem;
import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.util.XMLUtil;

import static org.junit.Assert.*;

public class TestAddressbook extends DomainTestBase {

	@Test
	public void testSaveGet() {
		AddressbookItem address = new AddressbookItem();
		address.setName("Jim");
		address.setEmail("jimg@someplace.com");
		address.setHomePhone("1231111");
		address.setMobilePhone("136544643433");
		address.save();

		AddressbookItem address2 = AddressbookItem.findById(address.getId());
		assertEquals("The two addressbook items are not equal", address,
				address2);
	}

	@Test
	public void testEmployeeAddressbook() {
		Employee employee = new Employee();
		employee.setName("某人");
		employee.setLoginName("moonranger");
		employee.setPhoneNumber("11111");
		employee.setLoginPassword("343434");
		employee.setSex("male");
		employee.save();

		AddressbookItem address = new AddressbookItem();
		address.setName("崔斯特");
		address.setEmail("jimg@someplace.com");
		address.setHomePhone("1231111");
		address.setMobilePhone("136544643433");
		address.setOwner(employee);
		address.save();

		AddressbookItem address2 = new AddressbookItem();
		address2.setName("Jerry");
		address2.setEmail("jerry@someplace.com");
		address2.setHomePhone("1231113");
		address2.setMobilePhone("136544643434");
		address2.setOwner(employee);
		address2.save();

		List<AddressbookItem> addressbook = employee.listAddressbook();
		String xml = XMLUtil.listToXML(addressbook, "addressbook");
		System.out.println("-----------------------------------------");
		System.out.println(xml);
		System.out.println("-----------------------------------------");
		assertTrue("The item is not included in this user's addressbook",
				addressbook.contains(address));
		assertTrue("The item is not included in this user's addressbook",
				addressbook.contains(address2));

	}
}
