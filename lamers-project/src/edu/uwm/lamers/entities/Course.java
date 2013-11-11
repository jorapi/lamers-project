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
	
	@Persistent
	private String courseLocation;
	
    //array to hold days of the week the class will meet 0 = sun, 1 = mon ... 
	//true if the course meets on that day
	@Persistent
	private Boolean[] DaysToMeet;
	
	@Persistent
	private String endTime;
	
	@Persistent
	private String startTime;
	


	
	/**
	 * 
	 * @param courseTitle the Title of the course
	 */
	public Course(String courseTitle) {
		this.courseTitle = courseTitle;
		studentList = new HashSet<Student>();
		DaysToMeet = createDaysArray();
	}
	
	public Course(String courseTitle, Instructor courseInstructor){
		this.courseInstructor = courseInstructor;
		this.courseTitle = courseTitle;
		studentList = new HashSet<Student>();
		DaysToMeet = createDaysArray();
	}
	
	public Course(String courseTitle, Instructor courseInstructor, String location, String start, String end){
		this.courseInstructor = courseInstructor;
		this.courseTitle = courseTitle;
		this.courseLocation = location;
		studentList = new HashSet<Student>();
		DaysToMeet = createDaysArray();
		this.endTime = end;
		this.startTime = start;
	}
	
	public Course(String courseTitle, String location, String start, String end){
		this.courseInstructor = null;
		this.courseTitle = courseTitle;
		this.courseLocation = location;
		studentList = new HashSet<Student>();
		DaysToMeet = createDaysArray();
		this.endTime = end;
		this.startTime = start;
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
		return studentList.remove(s);
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
	 * @return courseLocation
	 */
	public String getLocation(){
		return courseLocation;
	}
	
	/**
	 * @param l Location of course
	 */
	public void setLocation(String l){
		this.courseLocation = l;
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
	
	public Boolean[] getDaysToMeet() {
		return DaysToMeet;
	}
	
	public void setMeetingDays(Boolean[] days){
		this.DaysToMeet = days;
	}

	public void addMeetingDay(String day) {
		if(day.equalsIgnoreCase("sunday"))
			this.DaysToMeet[0] = true;
		else if(day.equalsIgnoreCase("monday"))
			this.DaysToMeet[1] = true;
		else if(day.equalsIgnoreCase("tuesday"))
			this.DaysToMeet[2] = true;
		else if(day.equalsIgnoreCase("wednesday"))
			this.DaysToMeet[3] = true;
		else if(day.equalsIgnoreCase("thursday"))
			this.DaysToMeet[4] = true;
		else if(day.equalsIgnoreCase("friday"))
			this.DaysToMeet[5] = true;
		else if(day.equalsIgnoreCase("saturday"))
			this.DaysToMeet[6] = true;
	}
	
	public void removeMeetingDay(String day){
		if(day.equalsIgnoreCase("sunday"))
			this.DaysToMeet[0] = false;
		else if(day.equalsIgnoreCase("monday"))
			this.DaysToMeet[1] = false;
		else if(day.equalsIgnoreCase("tuesday"))
			this.DaysToMeet[2] = false;
		else if(day.equalsIgnoreCase("wednesday"))
			this.DaysToMeet[3] = false;
		else if(day.equalsIgnoreCase("thursday"))
			this.DaysToMeet[4] = false;
		else if(day.equalsIgnoreCase("friday"))
			this.DaysToMeet[5] = false;
		else if(day.equalsIgnoreCase("saturday"))
			this.DaysToMeet[6] = false;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	private Boolean[] createDaysArray(){
		Boolean[] tempDays = new Boolean[7];
		
		for (int i = 0; i < 7; i++){
			tempDays[0] = false;
		}
		
		return tempDays;
	}
}
