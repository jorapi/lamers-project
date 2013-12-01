package edu.uwm.lamers.entities;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class User {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String firstName;
	
	@Persistent
	private String lastName;
	
	@Persistent
	private String email;
	
	@Persistent
	private String password;
	
	/**
	 * @param firstName the student's first name
	 * @param lastName the student's last name
	 * @param email the student's email address
	 */
	public User(String firstName, String lastName, String email){
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return the User's name, in format "Firstname Lastname"
	 */
	public String getName(){
		return ("" + firstName + " " + lastName);
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	
	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
	
	/**
	 * @return if current User is Student
	 */
	public boolean isStudent(){
		return (this.getClass() == Student.class);
	}
	
	/**
	 * @return if current User is Instructor
	 */
	public boolean isInstructor(){
		return (this.getClass() == Instructor.class);
	}
	
	/**
	 * @return if current User is Admin
	 */
	public boolean isAdmin(){
		return (this.getClass() == Admin.class);
	}
	
}
