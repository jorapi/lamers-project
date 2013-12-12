package edu.uwm.lamers.entities;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unowned;

@PersistenceCapable
public class Family {
	
	@Persistent
	@Unowned
	private Student head;
	
	@Persistent
	@Unowned
	private Set<Student> members;
	
	
	
	
public Family(){
	
	head=null;	
	members=new HashSet<Student>();
	
}
	


public Student getHead(){
	return this.head;
		
}

public Set<Student> getFamily(){
	return members;
}
	

public void addHead(Student s){
	this.head=s;
	
	
}

public void addMember(Student s){
	
		members.add(s);	
	
	
}


}
