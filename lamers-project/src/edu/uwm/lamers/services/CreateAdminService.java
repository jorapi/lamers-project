package edu.uwm.lamers.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;

import edu.uwm.lamers.entities.Admin;
import edu.uwm.lamers.entities.Instructor;
import edu.uwm.lamers.entities.User;

public class CreateAdminService {
	
	Set<String> errors = new HashSet<String>();
	
	public Set<String> createAdmin(String firstName, String lastName, String email, String password){
		
		PersistenceManager pm = getPersistenceManager();
		
		for (User u : (List<User>) pm.newQuery(User.class).execute()) {
			if (u.getEmail().equals(email))
				errors.add("User already exists with given email");
		}
		
		if(firstName.isEmpty()){
			errors.add("First Name cannot be null");
		} else if(lastName.isEmpty()){
			errors.add("Last Name cannot be null");
		} else if(firstName.isEmpty()){
			errors.add("Email cannot be null");
		} else if(password.isEmpty()){
			errors.add("Password cannot be null");
		}
		
		if (errors.isEmpty()){
			Admin a = new Admin(firstName, lastName, email);
			a.setPassword(password);
			
			try {
				pm.makePersistent(a);
			} finally {
				pm.close();
			}
		}
		
		return errors;
	}
	
	private PersistenceManager getPersistenceManager() {
		return JDOHelper.getPersistenceManagerFactory("transactions-optional").getPersistenceManager();
	}
}