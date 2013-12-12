package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.Student;

public class TestCourse {

	Course c;
	String title = "CS361";
	String location = "E100";
	Date startDate = new Date();
	Date endDate = new Date();
	String startTime = "1:00 PM";
	String endTime = "1:50 PM";
	Boolean[] days = new Boolean[7];
	double standardCost = 100;
	double familyCost  = 75;
	String billingCycle = "Monthly";
	Instructor instructor = new Instructor("John", "Smith", "js@gmail.com");
	
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp(){
		for (int i=0; i < days.length; i++) {
			if (i == 1 || i == 3 ) {
				days[i] = true;
			}
		}
		
		startDate.setMonth(1);
		startDate.setDate(21);
		startDate.setYear(2014);
		
		endDate.setMonth(5);
		endDate.setDate(16);
		endDate.setYear(2014);
		
		c = new Course(title, location, startDate, endDate,
				startTime, endTime, days, standardCost, 
				familyCost, billingCycle, instructor);
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
	public void testContainsStudent() {
		Student s1 = new Student("John", "Smith", "jsmith@gmail.com");
		Student s2 = new Student("Jane", "Witherspoon", "jwitherspoon@gmail.com");
		Student s3 = new Student("Dale", "Hampton", "dhampton@gmail.com");
		
		c.addStudent(s1);
		c.addStudent(s2);
		
		assertFalse(c.containsStudent(s3));
		assertTrue(c.containsStudent(s1));
		
	}
}
