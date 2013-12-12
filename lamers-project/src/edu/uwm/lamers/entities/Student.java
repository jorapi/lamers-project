package edu.uwm.lamers.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
	private boolean familyMember;
	
	@Persistent
	private boolean headOfFamily;
	
	@Persistent
	@Unowned
	private Demographic demo;

	@Persistent
	@Unowned
	private Set<Course> courses;
	
	@Persistent
	@Unowned
	private Set<Award> awards;
	
	@Persistent
	private Set<PaymentPlan> paymentPlans;
	
	@Persistent
	private double balance;
	
	@Persistent
	@Unowned
	private Map<Course, String> daysMissed;
	
	public Student(String firstName, String lastName, String email) {
		super(firstName, lastName, email);
		courses = new HashSet<Course>();
		awards = new HashSet<Award>();
		paymentPlans = new HashSet<PaymentPlan>();
	}

	public boolean isFamilyMember() {
		return familyMember;
	}

	public void setFamilyMember(boolean familyMember) {
		this.familyMember = familyMember;
	}

	public boolean isHeadOfFamily() {
		return headOfFamily;
	}

	public void setHeadOfFamily(boolean headOfFamily) {
		this.headOfFamily = headOfFamily;
	}

	public Demographic getDemo() {
		return demo;
	}

	public void setDemo(Demographic demo) {
		this.demo = demo;
	}

	public Set<Course> getCourses() {
		return courses;
	}

	public void addCourse(Course c) {
		if (c != null && !courses.contains(c)) {
			courses.add(c);
			if (!familyMember) {
				paymentPlans.add(new PaymentPlan(c.getKey().getId(),
						c.getStandardCost(), c.getBillingCycle(),
						c.getStartDate(), c.getEndDate()));
				balance += c.getStandardCost();

			} else if (familyMember && !headOfFamily) {
				paymentPlans.add(new PaymentPlan(c.getKey().getId(),
						0.0, c.getBillingCycle(),
						c.getStartDate(), c.getEndDate()));

			} else if (familyMember && headOfFamily) {
				paymentPlans.add(new PaymentPlan(c.getKey().getId(),
						c.getFamilyCost(), c.getBillingCycle(),
						c.getStartDate(), c.getEndDate()));
				balance += c.getFamilyCost();
			}
		}
	}
	
	public void removeCourse(Course c) {
		if (c != null && courses.contains(c)) {
			courses.remove(c);
			for (PaymentPlan p : paymentPlans) {
				if (c.getKey().getId() == p.getCourseID()) {
					paymentPlans.remove(p);
				}
			}
		}
	}

	public void removeCourses(Set<Course> courses) {
		for (Course c : courses) {
			removeCourse(c);
		}
	}

	public Set<Award> getAwards() {
		return awards;
	}

	public void addAward(Award a) {
		awards.add(a);
	}
	public void removeAward(Award a) {
		if (a != null && courses.contains(a)) {
			courses.remove(a);
		}
	}

	public Set<PaymentPlan> getPaymentPlans() {
		return paymentPlans;
	}

	public void setPaymentPlans(Set<PaymentPlan> pp) {
		this.paymentPlans = pp;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
	
	public Map<Course, String> getDaysMissed(){
		if(daysMissed == null) daysMissed = new HashMap<Course, String>();
		return daysMissed;
	}
	
	public void addDayMissed(Course c, int weekNum, int dayNum){
		if(daysMissed == null) daysMissed = new HashMap<Course, String>();
		if(!daysMissed.containsKey(c)){
			daysMissed.put(c, "" + weekNum + "-" + dayNum);
		} else {
			String temp = daysMissed.get(c);
			String temp2 = temp + "*" + weekNum + "-" + dayNum;
			daysMissed.put(c, temp2);
		}
	}
	
	public HashMap<Integer, Integer> getDaysForCourse(Course c){
		if(daysMissed == null) daysMissed = new HashMap<Course, String>();
		if(!daysMissed.containsKey(c)) return null;
		
		String daysMissed2 = daysMissed.get(c);
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		
		for(String s : daysMissed2.split("\\*")){
			if(s.isEmpty()) break;
			String[] strings = s.split("-");
			map.put(Integer.parseInt(strings[0]), Integer.parseInt(strings[1]));
		}
		
		return map;
	}
	
	public void clearCourseDaysMissed(Course c){
		if(daysMissed == null) daysMissed = new HashMap<Course, String>();
		if(!daysMissed.containsKey(c)) return;
		
		daysMissed.remove(c);
	}
}
