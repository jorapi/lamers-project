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
public class Student extends User {
	
	@Persistent
	@Unowned
	private Set<Course> coursesEnrolled;
	
	@Persistent
	@Unowned
	private Set<Award> awards;
	
	@Persistent
	@Unowned
	private PaymentPlan payPlan;
	
	@Persistent
	@Unowned
	private Demographic demo;
	
	/**
	 * @return the awards
	 */
	public Set<Award> getAwards() {
		return awards;
	}

	/**
	 * @return the demo
	 */
	public Demographic getDemo() {
		return demo;
	}

	/**
	 * @param demo the demo to set
	 */
	public void setDemo(Demographic demo) {
		this.demo = demo;
	}

	public Student(String firstName, String lastName, String email) {
		super(firstName, lastName, email);
		coursesEnrolled = new HashSet<Course>();
		payPlan = new PaymentPlan();
	}
	
	/**
	 * @return student's current balance
	 */
	public double getBalance(){
		return payPlan.getBalance();
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
		if (coursesEnrolled.add(c)){
			payPlan.addToBalance(c.getCost());
			return true;
		}
		
		return false;
	}
	
	/**
	 * @param c Course to be removed
	 * @return if remove was successful or not
	 */
	public boolean removeCourse(Course c){
		if (coursesEnrolled.remove(c)){
			payPlan.removeFromBalance(c.getCost());
			return true;
		}
		
		return false;
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
	
}
