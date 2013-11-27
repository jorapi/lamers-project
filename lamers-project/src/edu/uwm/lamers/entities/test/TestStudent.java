package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import java.util.Date;

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
		
		assertTrue(s.getBalance() == c.getStandardCost());
		
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
	
	/*
	 	
	@Test
	public void testAddToBalance() {
		p.addToBalance(250.00);
		
		assertTrue(p.getBalance() == 4685.00);
	}
	
	@Test
	public void testRemoveFromBalance() {
		try{
			p.removeFromBalance(5000.0);
			fail("Exception not thrown (removed more than balance contained)");
		} catch (IllegalArgumentException e){
			//do nothing
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
	}
	
	@Test
	public void testMakePaymentInvalidAmount() {
		assertFalse(p.makePayment(p.getMinimumPayment()-1, p.getDueDate()));
	}
	
	@Test
	public void testMakePaymentInvalidDate() {
		c3 = (Date) p.getDueDate().clone();
		c3.setMonth(c1.getDate() + 2);
		assertFalse(p.makePayment(p.getMinimumPayment(), c3));
	}
	
	@Test
	public void testPreviousPayments() {
		for (int i=1; p.makePayment(p.getMinimumPayment(), p.getDueDate()) != false; i++) {
			assertTrue(p.getPreviousPayments().size() == i);
		}
		assertTrue(p.getBalance() == 0);
	} 
	 */
}
