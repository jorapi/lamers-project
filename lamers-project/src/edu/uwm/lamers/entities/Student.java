package edu.uwm.lamers.entities;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

@PersistenceCapable
public class Student extends User {
	
	@Persistent
	private double balance;
	
	@Persistent
	private Set<Course> coursesEnrolled;
	
	@Persistent
	private PaymentPlan payPlan;
	
	public Student(String firstName, String lastName, String email) {
		super(firstName, lastName, email);
		balance = 0.00;
		coursesEnrolled = new HashSet<Course>();
	}
	
	/**
	 * @return student's current balance
	 */
	public double getBalance(){
		return balance;
	}
	
	/**
	 * @param balance Balance to be set
	 */
	public void setBalance(double balance){
		this.balance = balance;
	}
	
	/**
	 * @param addition amount to add to balance
	 */
	public void addToBalance(double addition){
		this.balance += addition;
	}
	
	/**
	 * @param remova amount to remove from balance
	 */
	public void removeFromBalance(double removal){
		if(this.balance < removal){
			throw new IllegalArgumentException();
		}
		this.balance -= removal;
	}
	
	/**
	 * @return set of student's enrolled Courses
	 */
	public Set<Course> getCourses(){
		return coursesEnrolled;
	}
	
	/**
	 * @param c Course to be added
	 * @return if add was successful or not
	 */
	public boolean addCourse(Course c){
		return coursesEnrolled.add(c);
	}
	
	/**
	 * @param c Course to be removed
	 * @return if remove was successful or not
	 */
	public boolean removeCourse(Course c){
		return coursesEnrolled.remove(c);
	}

	/**
	 * @param p Payment Plan to be set to student
	 */
	public void setPaymentPlan(PaymentPlan p) {
		this.payPlan = p;
	}

	/**
	 * @return student's Payment Plan
	 */
	public PaymentPlan getPaymentPlan() {
		return payPlan;
	}
	
	public void makePayment(){
		balance -= payPlan.getPaymentAmount();
	}
	
}
