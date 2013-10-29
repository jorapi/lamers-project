package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.uwm.lamers.entities.Student;

public class TestStudent {

	@Test
	public void testStudentConstructor() {
		Student s = new Student("John", "Smith", "jsmith@gmail.com");
		
		assertEquals(s.getFirstName(), "John");
		assertEquals(s.getLastName(), "Smith");
		assertEquals(s.getEmail(), "jsmith@gmail.com");
	}

}
