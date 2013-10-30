package edu.uwm.lamers.entities;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Student extends User {
	
	public Student(String firstName, String lastName, String email) {
		super(firstName, lastName, email);
		
	}
	
}
