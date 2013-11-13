package edu.uwm.lamers.entities;


import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import edu.uwm.lamers.entities.Payment;

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
	private Date startDate;

	@Persistent
	private Date dueDate;

	@Persistent
	private Date endDate;

	@Persistent
	private double balance;

	@Persistent
	private double minimumPayment;

	@Persistent
	private Set<Payment> previousPayments = new HashSet<Payment>();

	@Persistent
	int numOfPayments;


	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}

	public PaymentPlan(Date start, Date end, double bal) {
		if (start.after(end)) {
			throw new IllegalStateException("start date must be before end date");
		} else if (bal < 0) {
			throw new IllegalArgumentException("balance must be greater than 0");
		}

		startDate = start;
		dueDate = startDate;
		endDate = end;
		balance = bal;
		setMinimumPayment();
	}
	
	public PaymentPlan() {
		setMinimumPayment();
	}

	public void setStartDate(Date start) {
		if (previousPayments.size() != 0) {
			throw new IllegalStateException();
		}
		startDate = start;
		dueDate = startDate;
		setMinimumPayment();
	}	

	public Date getStartDate() {
		return startDate;
	}	

	public void setEndtDate(Date end) {
		endDate = end;
		setMinimumPayment();
	}	

	public Date getEndDate(Calendar end) {
		return endDate;
	}
	
	public Date getDueDate() {
		return dueDate;
	}

	public void setBalance(double bal) {
		balance = bal;
		setMinimumPayment();
	}

	public void addToBalance(double amount) {
		balance += amount;
		
		if(endDate != null && dueDate != null){
			setMinimumPayment();
		}
	}

	public void removeFromBalance(double amount) {
		if (amount > balance) {
			throw new IllegalArgumentException();
		}
		balance -= amount;
		setMinimumPayment();
	}

	public double getBalance() {
		return balance;
	}

	public boolean makePayment(double amount, Date date) {
		if (balance == 0) {
			return false;
		}
		if (amount < minimumPayment || amount > balance) {
			return false;
		} else if (date.after(dueDate)) {
			return false;
		}

		balance -= amount;
		setMinimumPayment();

		
		if (dueDate.getMonth() == 11){
			dueDate.setMonth(0);
		} else {
			dueDate.setMonth(dueDate.getMonth() + 1);
		}
		
		previousPayments.add(new Payment(amount,date));

		return true;
	}

	public Set<Payment> getPreviousPayments() {
		return previousPayments;
	}

	private void setMinimumPayment() {
		if (balance <= 100) {
			minimumPayment = balance;
		} else {
			int length = endDate.getMonth() - dueDate.getMonth();
			minimumPayment = balance / length;
		}
	}
	
	public double getMinimumPayment() {
		return minimumPayment;
	}
}