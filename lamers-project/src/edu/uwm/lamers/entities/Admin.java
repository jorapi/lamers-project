package edu.uwm.lamers.entities;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Admin extends User {

	public Admin(String firstName, String lastName, String email) {
		super(firstName, lastName, email);
	}

}
