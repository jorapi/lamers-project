package edu.uwm.lamers.entities;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Course {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String courseTitle;
	
	@Persistent
	private Instructor courseInstructor;
	
	@Persistent
	private Set<Student> studentList;
	
	/**
	 * 
	 * @param courseTitle the Title of the course
	 */
	public Course(String courseTitle) {
		this.courseTitle = courseTitle;
		studentList = new HashSet<Student>();
	}
	
	public Course(String courseTitle, Instructor courseInstructor){
		this.courseInstructor = courseInstructor;
		this.courseTitle = courseTitle;
		studentList = new HashSet<Student>();
	}
	
	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
	
	/**
	 * @return the number of enrolled students
	 */
	public int size() {
		return studentList.size();
	}

	/**
	 * 
	 * @param s Student to be added to course
	 * @return if student was added successfully
	 */
	public boolean addStudent(Student s) {
		if(this.containsStudent(s)) return false;
		return studentList.add(s);
	}
	
	/**
	 * 
	 * @param ss Set of Students to be added to course
	 * @return if any students were added successfully
	 */
	public boolean addMultipleStudents(Set<Student> ss) {
		boolean added = false;
		for (Student s : ss) {
			if (!this.containsStudent(s)) {
				studentList.add(s);
				added = true;
			}
		}
		return added;
	}

	/**
	 * 
	 * @param s Student to check
	 * @return if Student is already in class
	 */
	public boolean containsStudent(Student s) {
		return studentList.contains(s);
	}
	
	/**
	 * @param s Student to be removed
	 * @return whether or not the removal was successful
	 */
	public boolean removeStudent(Student s) {
		if (containsStudent(s)) {
			studentList.remove(s);
		}
		return false;
	}
	
	/**
	 * @param s Set of Students to be removed
	 * @return whether or not any students were removed successfully
	 */
	public boolean removeMultipleStudents(Set<Student> ss) {
		boolean removed = false;
		for (Student s : ss) {
			if (this.containsStudent(s)) {
				studentList.remove(s);
				removed = true;
			}
		}
		return removed;
	}
	
	/**
	 * 
	 * @return Course Instructor
	 */
	public Instructor getInstructor(){
		return courseInstructor;
	}
	
	/**
	 * 
	 * @param i Instructor to be set to course
	 */
	public void setInstructor(Instructor i){
		this.courseInstructor = i;
	}
	
	/**
	 * 
	 * @return Course title
	 */
	public String getTitle(){
		return courseTitle;
	}
	
	/**
	 * 
	 * @param courseTitle The title of the course
	 */
	public void setTitle(String courseTitle){
		this.courseTitle = courseTitle;
	}
}
