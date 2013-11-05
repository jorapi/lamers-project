package edu.uwm.lamers.entities;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

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
	 * @param s Student to check
	 * @return if Student is already in class
	 */
	public boolean containsStudent(Student s) {
		return studentList.contains(s);
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
