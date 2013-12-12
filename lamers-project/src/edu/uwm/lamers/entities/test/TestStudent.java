package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.PaymentPlan;
import edu.uwm.lamers.entities.Student;

public class TestStudent {

	Student s;
	PaymentPlan p1;
	PaymentPlan p2;
	PaymentPlan p3;
	
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
	
	@SuppressWarnings("deprecation")
	@Before
	public void setUp(){
		s = new Student("John", "Smith", "jsmith@gmail.com");
		
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
	public void testStudentConstructor() {
		
		assertEquals(s.getFirstName(), "John");
		assertEquals(s.getLastName(), "Smith");
		assertEquals(s.getEmail(), "jsmith@gmail.com");
	}
	
	@Test
	public void testAddRemoveCourse(){		
		//s.addCourse(c);
		//assertTrue(s.getCourses().size() == 1);
		
		//assertTrue(s.getBalance() == c.getStandardCost());
		
		s.removeCourse(c);
		assertTrue(s.getCourses().size() == 0);
		
		assertTrue(s.getBalance() == 0);
	}
	
	@Test
	public void testIsStudent(){
		assertTrue(s.isStudent());
		assertFalse(s.isAdmin());
		assertFalse(s.isInstructor());
	}
	
	@Test
	public void testAttendenceManagement(){
		assertTrue(s.getDaysForCourse(c) == null);
		
		s.addDayMissed(c, 0, 2);
		
		String[] missingDays = s.getDaysMissed().get(c).split("\\*");
		
		String[] daysStrings = missingDays[0].split("-");
		
		assertTrue(Integer.parseInt(daysStrings[0]) == 0);
		assertTrue(Integer.parseInt(daysStrings[1]) == 2);
		
		HashMap<Integer, Integer> missedMap = s.getDaysForCourse(c);
		
		assertTrue(missedMap.containsKey(0));
		assertTrue(missedMap.get(0) == 2);
		
		s.addDayMissed(c, 1, 2);
		
		missingDays = s.getDaysMissed().get(c).split("\\*");
		
		daysStrings = missingDays[1].split("-");
		
		assertTrue(Integer.parseInt(daysStrings[0]) == 1);
		assertTrue(Integer.parseInt(daysStrings[1]) == 2);
		
		missedMap = s.getDaysForCourse(c);
		
		assertTrue(missedMap.containsKey(1));
		assertTrue(missedMap.get(1) == 2);
	}
}
