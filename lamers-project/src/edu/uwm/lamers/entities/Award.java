package edu.uwm.lamers.entities;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Discriminator;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
@Discriminator(strategy=DiscriminatorStrategy.CLASS_NAME)
public class Award {
	
	@Persistent
	private String awardTitle;
	
	@Persistent
	private int awardLevel;
	
	@Persistent
	private int cost;
	
	@Persistent
	@Unowned
	private Set<Student> studentsWithAward;

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	public Award(String awardTitle, int awardLevel, int cost){
		this.awardLevel = awardLevel;
		this.awardTitle = awardTitle;
		this.cost = cost;
		studentsWithAward = new HashSet<Student>();
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
	
	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	/**
	 * 
	 * @param s Student to add
	 * @return if Student was added
	 */
	public boolean addStudent(Student s){
		if (this.containsStudent(s)) return false;
		return studentsWithAward.add(s);
	}
	
	/**
	 * 
	 * @param s Student to remove
	 * @return if Student was removed
	 */
	public boolean removeStudent(Student s){
		if (s != null && studentsWithAward.contains(s)) {
			studentsWithAward.remove(s);
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param s Student to check
	 * @return if Demographic contains student
	 */
	public boolean containsStudent(Student s){
		return studentsWithAward.contains(s);
	}
	
}
