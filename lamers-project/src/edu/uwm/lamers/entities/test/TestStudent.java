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
	public void testBalance() {
		
		assertTrue(s.getBalance() == 0.0);
		
		s.setBalance(100.00);
		
		assertTrue(s.getBalance() == 100.0);
	}
	
	@Test
	public void testAddToBalance(){
		s.addToBalance(100.00);
		
		assertTrue(s.getBalance() == 100.0);
	}
	
	@Test
	public void testRemoveFromBalance(){
		s.setBalance(100.0);
		s.removeFromBalance(50.0);
		
		assertTrue(s.getBalance() == 50.0);
		
		try{
			s.removeFromBalance(100.0);
			fail("Exception not thrown (removed more than balance contained)");
		} catch (IllegalArgumentException e){
			//do nothing
		} catch (Exception e){
			fail("Wrong exception thrown");
		}
	}
	
	@Test
	public void testAddRemoveCourse(){
		Course c = new Course();
		
		s.addCourse(c);
		assertTrue(s.getCourses().size() == 1);
		
		s.removeCourse(c);
		assertTrue(s.getCourses().size() == 0);
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
}
