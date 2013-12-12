package edu.uwm.lamers.entities;


import java.util.Date;

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
	private String billingCycle;

	@Persistent
	private Date startDate;

	@Persistent
	private Date dueDate;

	@Persistent
	private Date endDate;

	@Persistent
	private double amount;

	@Persistent
	int numOfPayments;


	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	public PaymentPlan(String bc, Date start, Date end, double amount) {
		billingCycle = bc;
		
		if (start.after(end)) {
			throw new IllegalStateException("start date must be before end date");
		} else if (amount < 0) {
			throw new IllegalArgumentException("balance must be greater than 0");
		}

		startDate = start;
		dueDate = startDate;
		endDate = end;
		this.amount = amount;
	}
	
	public void setStartDate(Date start) {
		startDate = start;
		dueDate = startDate;
	}

	public Date getStartDate() {
		return startDate;
	}	

	public void setEndtDate(Date end) {
		endDate = end;
	}	

	public Date getEndDate(Date end) {
		return endDate;
	}
	
	public Date getDueDate() {
		return dueDate;
	}
	
	public void updateDueDate() {
		
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public double getAmount() {
		return amount;
	}
}