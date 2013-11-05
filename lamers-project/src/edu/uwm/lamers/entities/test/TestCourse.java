package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;

public class TestCourse {

	Course c;
	
	@Before
	public void setUp(){
		c = new Course("Test Course");
	}

	@Test
	public void testAddStudent() {
		Student s1 = new Student("John", "Smith", "jsmith@gmail.com");
		
		c.addStudent(s1);
		assertTrue(c.containsStudent(s1));
	}
	
	@Test
	public void testSetInstructor(){
		Instructor i = new Instructor("John", "Smith", "jsmith@gmail.com");
		
		c.setInstructor(i);
		assertTrue(c.getInstructor() == i);
		
		c = new Course("Test Course", i);
		assertTrue(c.getInstructor() == i);
	}

}
