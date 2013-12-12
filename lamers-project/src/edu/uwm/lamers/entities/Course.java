package edu.uwm.lamers.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Course {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	@Persistent
	private String title;
	
	@Persistent
	private String location;
	
	@Persistent
	@Unowned
	private Set<Award> requirements;
	
	@Persistent
	private Date startDate;
	
	@Persistent
	private Date endDate;
	
	@Persistent
	private int numOfWeeks;
	
	@Persistent
	private String startTime;
	
	@Persistent
	private String endTime;
	
	@Persistent
	private Boolean[] daysToMeet;
	
	@Persistent
	private double standardCost;
	
	@Persistent
	private double familyCost;
	
	@Persistent
	private String billingCycle;
	
	@Persistent
	@Unowned
	private Instructor instructor;
	
	@Persistent
	private Set<Student> students;
	
	@SuppressWarnings("deprecation")
	public Course(String title, String location, Date startDate, Date endDate,
			String startTime, String endTime, Boolean[] days, double standardCost, 
			double familyCost, String billingCycle, Instructor instructor) {
		this.title = title;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
		numOfWeeks = (endDate.getMonth() - startDate.getMonth()) * 4;
		this.startTime = startTime;
		this.endTime = endTime;
		daysToMeet = days;
		this.standardCost = standardCost;
		this.familyCost = familyCost;
		this.billingCycle = billingCycle;
		this.instructor = instructor;
		students = new HashSet<Student>();
	}

	public Key getKey() {
		return key;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public int getNumOfWeeks() {
		return numOfWeeks;
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

	public Set<String> getDaysToMeet(){
		Set<String> days = new HashSet<String>();
		
		if(daysToMeet[0] != null && daysToMeet[0] == true) { days.add("Sunday"); }
		if(daysToMeet[1] != null && daysToMeet[1] == true) { days.add("Monday"); }
		if(daysToMeet[2] != null && daysToMeet[2] == true) { days.add("Tuesday"); }
		if(daysToMeet[3] != null && daysToMeet[3] == true) { days.add("Wednesday"); }
		if(daysToMeet[4] != null && daysToMeet[4] == true) { days.add("Thursday"); }
		if(daysToMeet[5] != null && daysToMeet[5] == true) { days.add("Friday"); }
		if(daysToMeet[6] != null && daysToMeet[6] == true) { days.add("Saturday"); }
		
		return days;
	}

	public void setDaysToMeet(Boolean[] daysToMeet) {
		this.daysToMeet = daysToMeet;
	}
	
	public void addMeetingDay(String day) {
		if (day.equalsIgnoreCase("Monday")) {
			daysToMeet[0] = true;
		} else if (day.equalsIgnoreCase("Tuesday")) {
			daysToMeet[1] = true;
		} else if (day.equalsIgnoreCase("Wednesday")) {
			daysToMeet[2] = true;
		} else if (day.equalsIgnoreCase("Thursday")) {
			daysToMeet[3] = true;
		} else if (day.equalsIgnoreCase("Friday")) {
			daysToMeet[4] = true;
		} else if (day.equalsIgnoreCase("Saturday")) {
			daysToMeet[5] = true;
		} else if (day.equalsIgnoreCase("Sunday")) {
			daysToMeet[6] = true;
		} 
	}
	
	public void removeMeetingDay(String day) {
		if (day.equalsIgnoreCase("Monday")) {
			daysToMeet[0] = false;
		} else if (day.equalsIgnoreCase("Tuesday")) {
			daysToMeet[1] = false;
		} else if (day.equalsIgnoreCase("Wednesday")) {
			daysToMeet[2] = false;
		} else if (day.equalsIgnoreCase("Thursday")) {
			daysToMeet[3] = false;
		} else if (day.equalsIgnoreCase("Friday")) {
			daysToMeet[4] = false;
		} else if (day.equalsIgnoreCase("Saturday")) {
			daysToMeet[5] = false;
		} else if (day.equalsIgnoreCase("Sunday")) {
			daysToMeet[6] = false;
		} 
	}

	public double getStandardCost() {
		return standardCost;
	}

	public void setStandardCost(double standardCost) {
		this.standardCost = standardCost;
	}

	public double getFamilyCost() {
		return familyCost;
	}

	public void setFamilyCost(double familyCost) {
		this.familyCost = familyCost;
	}

	public String getBillingCycle() {
		return billingCycle;
	}

	public void setBillingCycle(String billingCycle) {
		this.billingCycle = billingCycle;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}
	
	/**
	 * @return the number of enrolled students
	 */
	public int size() {
		return students.size();
	}

	/**
	 * 
	 * @param s Student to be added to course
	 * @return if student was added successfully
	 */
	public boolean addStudent(Student s) {
		if (this.containsStudent(s)) return false;
		return students.add(s);
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
				students.add(s);
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
		return students.contains(s);
	}
	
	/**
	 * @param s Student to be removed
	 * @return whether or not the removal was successful
	 */
	public boolean removeStudent(Student s) {
		return students.remove(s);
	}
	
	/**
	 * @param s Set of Students to be removed
	 * @return whether or not any students were removed successfully
	 */
	public boolean removeMultipleStudents(Set<Student> ss) {
		boolean removed = false;
		for (Student s : ss) {
			if (this.containsStudent(s)) {
				students.remove(s);
				removed = true;
			}
		}
		return removed;
	}
	
	public Set<Award> getRequirements() {
		return requirements;
	}

	public void setRequirements(Set<Award> requirements) {
		this.requirements = requirements;
	}
	
	public void addRequirement(Award a){
		if(a != null)
			if (!requirements.contains(a)) {
				requirements.add(a);
			}
	}
	
	public boolean removeRequirement(Award a){
		if (a != null && requirements.contains(a)) {
			requirements.remove(a);
			return true;
		}
		return false;
	}
}