package edu.uwm.lamers.entities;

import java.util.Calendar;
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
	private Calendar startDate;

	@Persistent
	private Calendar dueDate;

	@Persistent
	private Calendar endDate;

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

	public PaymentPlan(Calendar start, Calendar end, double bal) {
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

	public void setStartDate(Calendar start) {
		if (previousPayments.size() != 0) {
			throw new IllegalStateException();
		}
		startDate = start;
		dueDate = startDate;
		setMinimumPayment();
	}	

	public Calendar getStartDate() {
		return startDate;
	}	

	public void setEndtDate(Calendar end) {
		endDate = end;
		setMinimumPayment();
	}	

	public Calendar getEndDate(Calendar end) {
		return endDate;
	}
	
	public Calendar getDueDate() {
		return dueDate;
	}

	public void setBalance(double bal) {
		balance = bal;
		setMinimumPayment();
	}

	public void addToBalance(double amount) {
		balance += amount;
		setMinimumPayment();
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

	public boolean makePayment(double amount, Calendar date) {
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

		dueDate.add(Calendar.MONTH, 1);
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
			int length = endDate.get(Calendar.MONTH) - dueDate.get(Calendar.MONTH);
			minimumPayment = balance / length;
		}
	}
	
	public double getMinimumPayment() {
		return minimumPayment;
	}
}