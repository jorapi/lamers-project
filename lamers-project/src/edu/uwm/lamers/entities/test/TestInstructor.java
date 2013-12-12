package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Instructor;

public class TestInstructor {

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
	Instructor instructor;
	
	@Before
	public void setUp(){
		instructor = new Instructor("John", "Smith", "jsmith@gmail.com");
	}
	
	@Test
	public void testAddRemoveCourse(){
		Course c = new Course(title, location, startDate, endDate,
				startTime, endTime, days, standardCost, 
				familyCost, billingCycle, instructor);
		
		instructor.addCourse(c);
		assertTrue(instructor.getCourses().size() == 1);
		
		instructor.removeCourse(c);
		assertTrue(instructor.getCourses().size() == 0);
	}
	
	@Test
	public void testIsInstructor(){
		assertFalse(instructor.isStudent());
		assertFalse(instructor.isAdmin());
		assertTrue(instructor.isInstructor());
	}

}
