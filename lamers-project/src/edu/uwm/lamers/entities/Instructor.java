package edu.uwm.lamers.entities;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Instructor extends User {
	
	@Persistent
	@Unowned
	private Set<Course> coursesTeaching;

	public Instructor(String firstName, String lastName, String email) {
		super(firstName, lastName, email);
		coursesTeaching = new HashSet<Course>();
	}
	
	/**
	 * @param c Course to be added
	 * @return if add was successful or not
	 */
	public boolean addCourse(Course c){
		return coursesTeaching.add(c);
	}
	
	/**
	 * @param c Course to be removed
	 * @return if remove was successful or not
	 */
	public boolean removeCourse(Course c){
		return coursesTeaching.remove(c);
	}

	/**
	 * @return the coursesTeaching
	 */
	public Set<Course> getCourses() {
		return coursesTeaching;
	}

}
