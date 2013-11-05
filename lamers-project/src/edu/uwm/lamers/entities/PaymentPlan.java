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
	Date startDate;
	
	@Persistent
	Date endDate;
	
	@Persistent
	int numOfPayments;
	
	public double getPaymentAmount(double balance) {
		return 0;
	}

	
	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
}
