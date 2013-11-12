package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.PaymentPlan;

public class TestPaymentPlan {

	PaymentPlan p;
	Calendar c1 = Calendar.getInstance();
	Calendar c2 = Calendar.getInstance();
	Calendar c3 = Calendar.getInstance();
	
	@Before
	public void setUp() throws Exception {
		testConstructor();
	}

	@Test
	public void testConstructor() {
		c1.set(2014, 1, 26);
		c2.set(2014, 5, 21);
		
		try {
			p = new PaymentPlan(c2, c1, 4435.00);
			fail("Exception not thrown (start date occurs after end date)");
		} catch (IllegalStateException e){
			// do nothing
		} catch (Exception e) {
			fail("Wrong exception thrown");
		}
		
		try {
			p = new PaymentPlan(c1, c2, -4435.00);
			fail("Exception not thrown (balance cannot be negative)");
		} catch (IllegalArgumentException e){
			// do nothing
		} catch (Exception e) {
			fail("Wrong exception thrown");
		}
		
		p = new PaymentPlan(c1, c2, 4435.00);	
	}
	
	@Test
	public void testSetStartDateAfterPayment() {
		p.makePayment(p.getMinimumPayment(), c1);
		
		c1.add(Calendar.MONTH, 1);
		
		try {
			p.setStartDate(c1);
			fail("Exception not thrown (changed start date after making payment)");
		} catch (IllegalStateException e) {
			// do nothing
		} catch (Exception e) {
			fail("Wrong exception thrown");
		}
	}
	
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
		c3 = (Calendar) p.getDueDate().clone();
		c3.add(Calendar.DAY_OF_MONTH, 1);
		assertFalse(p.makePayment(p.getMinimumPayment(), c3));
	}
	
	@Test
	public void testPreviousPayments() {
		for (int i=1; p.makePayment(p.getMinimumPayment(), p.getDueDate()) != false; i++) {
			assertTrue(p.getPreviousPayments().size() == i);
		}
		assertTrue(p.getBalance() == 0);
	}
}