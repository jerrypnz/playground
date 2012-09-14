package com.jstudio.jrestoa.domain.test;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Test;

import com.jstudio.jrestoa.domain.Employee;
import com.jstudio.jrestoa.domain.ToDo;

import static org.junit.Assert.*;

public class TestTodos extends DomainTestBase {

	@Test
	public void testSaveGet() {
		Employee emp = new Employee();
		emp.setName("Jerry");
		emp.setLoginName("moonranger");
		emp.setPhoneNumber("11111");
		emp.setLoginPassword("343434");
		emp.setSex("male");
		emp.save();

		ToDo todo1 = new ToDo();
		todo1.setOwner(emp);
		todo1.setTodo("Checkout newest code and build them");
		todo1.setAlarmTime(Timestamp.valueOf("2008-05-17 16:30:00"));
		todo1.save();

		ToDo todo2 = ToDo.findById(todo1.getId());

		assertEquals("Todo is not correctly saved.", todo1, todo2);
	}

	@Test
	public void testTodoNotice() {
		Employee emp = new Employee();
		emp.setName("Jerry");
		emp.setLoginName("moonranger");
		emp.setPhoneNumber("11111");
		emp.setLoginPassword("343434");
		emp.setSex("male");
		emp.save();

		ToDo todo1 = new ToDo();
		todo1.setOwner(emp);
		todo1.setTodo("Checkout newest code and build them");
		todo1.setAlarmTime(Timestamp.valueOf("2008-05-17 16:30:00"));
		todo1.save();

		ToDo todo2 = new ToDo();
		todo2.setOwner(emp);
		todo2.setTodo("blah,blah,blah,blah");
		todo2.setAlarmTime(Timestamp.valueOf("2008-05-17 18:30:00"));
		todo2.save();

		ToDo todo3 = new ToDo();
		todo3.setOwner(emp);
		todo3.setTodo("foofoofoofoofoofoofoo");
		todo3.setAlarmTime(Timestamp.valueOf("2008-05-17 16:30:00"));
		todo3.setFinished(true);
		todo3.save();

		List<ToDo> all = emp.listAllToDos();
		assertTrue(all.contains(todo1));
		assertTrue(all.contains(todo2));
		assertTrue(all.contains(todo3));

		List<ToDo> shouldNotice = emp.listNoticeToDos(Timestamp
				.valueOf("2008-05-17 16:31:00"));
		assertTrue(shouldNotice.contains(todo1));
		assertFalse(shouldNotice.contains(todo2));
		assertFalse(shouldNotice.contains(todo3));
		
		List<ToDo> finished = emp.listFinishedToDos();
		assertFalse(finished.contains(todo1));
		assertFalse(finished.contains(todo2));
		assertTrue(finished.contains(todo3));
		
	}
}
