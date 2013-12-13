package edu.uwm.lamers.services.test;

import static org.junit.Assert.*;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import edu.uwm.lamers.entities.Admin;
import edu.uwm.lamers.services.CreateAdminService;

public class TestCreateAdminService {
	
	/*Admin a;
	CreateAdminService cas;
	PersistenceManager pm = JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	
	@Before
	public void setUp(){
		 cas = new CreateAdminService();
		 a = new Admin("David", "Jones", "djones@gmail.com");
		 
		 pm.makePersistent(a);
	}
	
	@Test
	public void testCreateAdminSuccessfully(){
		assertTrue(cas.createAdmin("Danny", "Avidan", "das@gmail.com", "notsogrump").isEmpty());
	}

	@After
	public void tearDown(){
		pm.deletePersistent(a);
	}*/
		
}