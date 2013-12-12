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
public class Demographic {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;
	
	@Persistent
	private String demoTitle;
	
	@Persistent
	@Unowned
	private Set<Student> studentsInDemo;
	
	public Demographic(String demoTitle){
		this.demoTitle = demoTitle;
		studentsInDemo = new HashSet<Student>();
	}
	
	/**
	 * 
	 * @param demoTitle Title to set
	 */
	public void setTitle(String demoTitle){
		this.demoTitle = demoTitle;
	}
	
	/**
	 * 
	 * @return Title of demographic
	 */
	public String getTitle(){
		return demoTitle;
	}
	
	/**
	 * 
	 * @param s Student to add
	 * @return if Student was added
	 */
	public boolean addStudent(Student s){
		return studentsInDemo.add(s);
	}
	
	/**
	 * 
	 * @param s Student to remove
	 * @return if Student was removed
	 */
	public boolean removeStudent(Student s){
		return studentsInDemo.remove(s);
	}
	
	/**
	 * 
	 * @param s Student to check
	 * @return if Demographic contains student
	 */
	public boolean containsStudent(Student s){
		return studentsInDemo.contains(s);
	}
	
	
	/**
	 * @return the key
	 */
	public Key getKey() {
		return key;
	}
}
