package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

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
	public void testAddMultipleStudents() {
		Set<Student> ss = new HashSet<Student>();
		Student s1 = new Student("John", "Smith", "jsmith@gmail.com");
		Student s2 = new Student("Jane", "Witherspoon", "jwitherspoon@gmail.com");
		Student s3 = new Student("Dale", "Hampton", "dhampton@gmail.com");
		
		ss.add(s1);
		ss.add(s2);
		ss.add(s3);
		
		c.addMultipleStudents(ss);
		
		assertEquals(3, c.size());
		assertTrue(c.containsStudent(s1));
		assertTrue(c.containsStudent(s2));
		assertTrue(c.containsStudent(s3));

	}
	
	@Test
	public void testAddDuplicateStudent() {
		Student s1 = new Student("John", "Smith", "jsmith@gmail.com");
		
		c.addStudent(s1);
		assertFalse(c.addStudent(s1));
	}
	
	@Test
	public void testRemoveStudent() {
		Student s1 = new Student("John", "Smith", "jsmith@gmail.com");
		Student s2 = new Student("Jane", "Witherspoon", "jwitherspoon@gmail.com");
		Student s3 = new Student("Dale", "Hampton", "dhampton@gmail.com");
		
		c.addStudent(s1);
		c.addStudent(s2);
		c.addStudent(s3);
		
		assertEquals(3, c.size());
		
		c.removeStudent(s2);
		
		assertFalse(c.containsStudent(s2));
		assertEquals(2, c.size());
	}
	
	@Test
	public void testRemoveMultitpleStudents() {
		Student s1 = new Student("John", "Smith", "jsmith@gmail.com");
		Student s2 = new Student("Jane", "Witherspoon", "jwitherspoon@gmail.com");
		Student s3 = new Student("Dale", "Hampton", "dhampton@gmail.com");
		
		c.addStudent(s1);
		c.addStudent(s2);
		c.addStudent(s3);
		
		assertEquals(3, c.size());
		
		c.removeStudent(s2);
		
		assertFalse(c.containsStudent(s2));
		assertEquals(2, c.size());
	}
	
	@Test
	public void testRemoveUnenrolledStudent() {
		Student s1 = new Student("John", "Smith", "jsmith@gmail.com");
		Student s2 = new Student("Jane", "Witherspoon", "jwitherspoon@gmail.com");
		Student s3 = new Student("Dale", "Hampton", "dhampton@gmail.com");
		
		c.addStudent(s1);
		c.addStudent(s2);
		
		assertFalse(c.removeStudent(s3));
	}
	
	@Test
	public void testSetInstructor(){
		Instructor i = new Instructor("John", "Smith", "jsmith@gmail.com");
		
		c.setInstructor(i);
		assertTrue(c.getInstructor() == i);
		
		c = new Course("Test Course", i);
		assertTrue(c.getInstructor() == i);
	}
	
	@Test
	public void testContainsStudent() {
		Student s1 = new Student("John", "Smith", "jsmith@gmail.com");
		Student s2 = new Student("Jane", "Witherspoon", "jwitherspoon@gmail.com");
		Student s3 = new Student("Dale", "Hampton", "dhampton@gmail.com");
		
		c.addStudent(s1);
		c.addStudent(s2);
		
		assertFalse(c.containsStudent(s3));
		assertTrue(c.containsStudent(s1));
		
	}
	
	@Test
	public void testAddMeetingDay(){
		c.addMeetingDay("Monday");
		c.addMeetingDay("ThuRsday");
		
		assertFalse(c.getDaysToMeet()[0]);
		assertTrue(c.getDaysToMeet()[1]);
		assertFalse(c.getDaysToMeet()[2]);
		assertFalse(c.getDaysToMeet()[3]);
		assertTrue(c.getDaysToMeet()[4]);
		assertFalse(c.getDaysToMeet()[5]);
		assertFalse(c.getDaysToMeet()[6]);
	}
	
	@Test
	public void testRemoveMeetingDay(){
		c.addMeetingDay("Sunday");
		c.addMeetingDay("Monday");
		c.addMeetingDay("ThuRsday");
		
		assertTrue(c.getDaysToMeet()[0]);
		
		c.removeMeetingDay("Sunday");
		
		assertFalse(c.getDaysToMeet()[0]);
		assertTrue(c.getDaysToMeet()[1]);
		assertFalse(c.getDaysToMeet()[2]);
		assertFalse(c.getDaysToMeet()[3]);
		assertTrue(c.getDaysToMeet()[4]);
		assertFalse(c.getDaysToMeet()[5]);
		assertFalse(c.getDaysToMeet()[6]);
	}
}
