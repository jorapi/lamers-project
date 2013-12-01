package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.Demographic;
import edu.uwm.lamers.entities.Student;

public class TestDemographic {

	Demographic d;
	
	@Before
	public void setUp(){
		d = new Demographic("Test");
	}
	
	@Test
	public void testAddRemoveStudent() {
		Student s = new Student("John", "Smith", "Test@aol.com");
		
		assertTrue(d.addStudent(s));
		
		assertTrue(d.containsStudent(s));
		
		assertTrue(d.removeStudent(s));
		
		assertFalse(d.containsStudent(s));
	}

}
