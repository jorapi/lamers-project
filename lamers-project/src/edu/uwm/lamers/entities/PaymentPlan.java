package edu.uwm.lamers.entities;

import java.util.Date;
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
public class PaymentPlan {

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private long courseID;
	
	@Persistent
	private double amount;
	
	@Persistent
	private double amountDue;
	
	@Persistent
	private String billingCycle;
	
	@Persistent
	private Date startDate;

	@Persistent
	private Date dueDate;

	@Persistent
	private Date endDate;
	
	@Persistent
	private Set<Payment> previousPayments;
	
	
	public PaymentPlan(long id, double amount, String bc, Date start, int numOfPayments) {
		if (amount < 0) {
			throw new IllegalArgumentException("balance must be greater than 0");
		}

		courseID = id;
		this.amount = amount;
		billingCycle = bc;
		startDate = start;
		dueDate = startDate;
		amountDue=amount/numOfPayments;
	}
	
	public long getCourseID() {
		return courseID;
	}
	
	public void setCourseID(long id) {
		courseID = id;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double getAmountDue() {
		return amountDue;
	}
	
	public void setAmountDue(double amount) {
		amountDue = amount;
	}
	
	public String getBillingCycle() {
		return billingCycle;
	}
	
	public void setBillingCycle(String bc) {
		billingCycle = bc;
	}
	
	public Date getStartDate() {
		return startDate;
	}	
	
	public void setStartDate(Date start) {
		startDate = start;
		dueDate = startDate;
	}
	
	public Date getEndDate(Date end) {
		return endDate;
	}
	
	public void setEndDate(Date end) {
		endDate = end;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(Date dd) {
		dueDate = dd;
	}
	
	public void updateDueDate() {
		if (billingCycle.equalsIgnoreCase("Flat Rate")) {
			dueDate = null;
		} else if (billingCycle.equalsIgnoreCase("Monthly")) {
			//TODO
		} else if (billingCycle.equalsIgnoreCase("Weekly")) {
			//TODO
		} else if (billingCycle.equalsIgnoreCase("Per session")) {
			//TODO
		}
	}
	
	public boolean makePayment(double amount, Date date) {
		if (amountDue == 0) {
			return false;
		}

		amountDue -= amount;
		
		updateDueDate();
		previousPayments.add(new Payment(amount,date));
		
		return true;
	}
	
	public Set<Payment> getPreviousPayments() {
		return previousPayments;
	}
}
