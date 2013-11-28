package edu.uwm.lamers.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import edu.uwm.lamers.entities.Payment;

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
	private Set<PaymentPlan> paymentPlans;
	
	@Persistent
	private Set<Payment> previousPayments = new HashSet<Payment>();
	
	@Persistent
	@Unowned
	private Demographic demo;
	
	@Persistent
	private double balance;
	
	@Persistent
	private Map<Course, ArrayList<String>> daysMissed;

	public Student(String firstName, String lastName, String email) {
		super(firstName, lastName, email);
		coursesEnrolled = new HashSet<Course>();
		daysMissed = new HashMap<Course, ArrayList<String>>();
	}
	
	/**
	 * @param a
	 * adds a to the current set of awards
	 */
	public void addAwards(Award a) {
		if (a != null && !awards.contains(a)) {
			awards.add(a);
		}
	}
	
	/**
	 * @return the awards
	 */
	public Set<Award> getAwards() {
		return awards;
	}
	
	/**
	 * @param demo the demo to set
	 */
	public void setDemo(Demographic d) {
		demo = d;
	}

	/**
	 * @return the demo
	 */
	public Demographic getDemo() {
		return demo;
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
		if (c != null && !coursesEnrolled.contains(c)) {
			coursesEnrolled.add(c);
			// implement a way to add that specific class' payment plan
			return true;
		}
		return false;
	}
	
	/**
	 * @param c Course to be removed
	 * @return if remove was successful or not
	 */
	public boolean removeCourse(Course c){
		if (c != null && coursesEnrolled.contains(c)) {
			coursesEnrolled.remove(c);
			// TODO
			// implement a way to remove that specific class' payment plan
			return true;
		}
		return false;
	}

	/**
	 * @return student's Payment Plan
	 */
	public Set<PaymentPlan> getPaymentPlans() {
		return paymentPlans;
	}
	
	public double getBalance() {
		return balance;
	}

	/* TODO
	 * implement payment system
	public boolean makePayment(double amount, Date date) {
		if (balance == 0) {
			return false;
		}
		if (amount > balance) {
			return false;
		} else if (date.after(dueDate)) {
			return false;
		}

		balance -= amount;
		
		if (dueDate.getMonth() == 11){
			dueDate.setMonth(0);
		} else {
			dueDate.setMonth(dueDate.getMonth() + 1);
		}
		
		previousPayments.add(new Payment(amount,date));

		return true;
	}
	*/
	public Set<Payment> getPreviousPayments() {
		return previousPayments;
	}
	
	public Map<Course, ArrayList<String>> getDaysMissed(){
		return daysMissed;
	}
	
	public void addDayMissed(Course c, int weekNum, int dayNum){
		if(daysMissed == null) daysMissed = new HashMap<Course, ArrayList<String>>();
		if(!daysMissed.containsKey(c)){
			daysMissed.put(c, new ArrayList<String>());
		}
		
		daysMissed.get(c).add("" + weekNum + "-" + dayNum);
	}
	
	public HashMap<Integer, Integer> getDaysForCourse(Course c){
		if(daysMissed == null) daysMissed = new HashMap<Course, ArrayList<String>>();
		if(!daysMissed.containsKey(c)) return null;
		
		ArrayList<String> daysMissed2 = daysMissed.get(c);
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(String s : daysMissed2){
			String[] strings = s.split("-");
			map.put(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
		}
		
		return map;
	}
}
