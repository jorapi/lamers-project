package edu.uwm.lamers.entities;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Award {
	
	@Persistent
	private String awardTitle;
	
	@Persistent
	private int awardLevel;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	public Award(String awardTitle, int awardLevel){
		this.awardLevel = awardLevel;
		this.awardTitle = awardTitle;
	}
	
	/**
	 * @return the awardTitle
	 */
	public String getAwardTitle() {
		return awardTitle;
	}

	/**
	 * @param awardTitle the awardTitle to set
	 */
	public void setAwardTitle(String awardTitle) {
		this.awardTitle = awardTitle;
	}

	/**
	 * @return the awardLevel
	 */
	public int getAwardLevel() {
		return awardLevel;
	}

	/**
	 * @param awardLevel the awardLevel to set
	 */
	public void setAwardLevel(int awardLevel) {
		this.awardLevel = awardLevel;
	}

	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
	
}
