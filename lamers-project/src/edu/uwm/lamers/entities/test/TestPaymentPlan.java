package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.PaymentPlan;

public class TestPaymentPlan {

	PaymentPlan p;
	Date c1 = new Date(11, 12, 13);
	Date c2 = new Date(11, 12, 13);
	Date c3 = new Date(11, 12, 13);
	
	@Before
	public void setUp() throws Exception {
		testConstructor();
	}

	@Test
	public void testConstructor() {
		c1.setYear(2014);
		c1.setMonth(1);
		c1.setDate(26);
		
		c2.setYear(2014);
		c2.setMonth(5);
		c2.setDate(21);
		
		try {
			p = new PaymentPlan("monthly", c2, c1, 75.00);
			fail("Exception not thrown (start date occurs after end date)");
		} catch (IllegalStateException e){
			// do nothing
		} catch (Exception e) {
			fail("Wrong exception thrown");
		}
		
		try {
			p = new PaymentPlan("monthly", c1, c2, -75.00);
			fail("Exception not thrown (balance cannot be negative)");
		} catch (IllegalArgumentException e){
			// do nothing
		} catch (Exception e) {
			fail("Wrong exception thrown");
		}
		
		p = new PaymentPlan("monthly", c1, c2, 75.00);	
	}
}