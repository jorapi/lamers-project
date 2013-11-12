package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.Course;
import edu.uwm.lamers.entities.PaymentPlan;
import edu.uwm.lamers.entities.Student;

public class TestStudent {

	Student s;
	PaymentPlan p1;
	PaymentPlan p2;
	PaymentPlan p3;
	
	@Before
	public void setUp(){
		s = new Student("John", "Smith", "jsmith@gmail.com");
		
	}
	
	@Test
	public void testStudentConstructor() {
		
		assertEquals(s.getFirstName(), "John");
		assertEquals(s.getLastName(), "Smith");
		assertEquals(s.getEmail(), "jsmith@gmail.com");
	}
	
	@Test
	public void testAddRemoveCourse(){
		Course c = new Course("Test Course");
		
		s.addCourse(c);
		assertTrue(s.getCourses().size() == 1);
		
		assertTrue(s.getPaymentPlan().getBalance() == c.getCost());
		
		s.removeCourse(c);
		assertTrue(s.getCourses().size() == 0);
		
		assertTrue(s.getPaymentPlan().getBalance() == 0);
	}
	
	@Test
	public void testIsStudent(){
		assertTrue(s.isStudent());
		assertFalse(s.isAdmin());
		assertFalse(s.isInstructor());
	}
	
	@Test
	public void testPaymentPlan(){
		s.setPaymentPlan(p1);
		assertTrue(p1 == s.getPaymentPlan());
	}
	
	@Test
	public void testPayment(){
		
	}
}
