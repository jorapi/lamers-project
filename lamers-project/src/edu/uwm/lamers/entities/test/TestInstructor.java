package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Instructor;

public class TestInstructor {

	Instructor i;
	
	@Before
	public void setUp(){
		i = new Instructor("John", "Smith", "jsmith@gmail.com");
	}
	
	@Test
	public void testAddRemoveCourse(){
		Course c = new Course("Test Course");
		
		i.addCourse(c);
		assertTrue(i.getCourses().size() == 1);
		
		i.removeCourse(c);
		assertTrue(i.getCourses().size() == 0);
	}
	
	@Test
	public void testIsInstructor(){
		assertFalse(i.isStudent());
		assertFalse(i.isAdmin());
		assertTrue(i.isInstructor());
	}

}
