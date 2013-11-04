package edu.uwm.lamers.entities;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Persistent;

public class Instructor extends User {
	
	@Persistent
	private Set<Course> coursesTeaching;

	public Instructor(String firstName, String lastName, String email) {
		super(firstName, lastName, email);
		coursesTeaching = new HashSet<Course>();
	}
	
	/**
	 * @return set of teachers's Courses
	 */
	public Set<Course> getCourses(){
		return coursesTeaching;
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

}
