package edu.uwm.lamers.entities.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.Admin;

public class TestAdmin {
	
	Admin a;
	
	@Before
	public void setUp(){
		a = new Admin("John", "Smith", "jsmith@gmail.com");
	}
	
	@Test
	public void testIsAdmin(){
		assertFalse(a.isStudent());
		assertTrue(a.isAdmin());
		assertFalse(a.isInstructor());
	}

}
