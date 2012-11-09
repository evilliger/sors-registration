//--------------------------------------//
// Name: Heat   						//
// Purpose: This class is to provide 	//
//		a  heat object with the same    //
// 		attributes as the event entity  //
//		in the datastore.				//
//--------------------------------------//
package com.registration.sors.model;
import java.io.Serializable;
import java.util.*;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class Heat implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	@Id
	Long id;
	public static Long parentId = new Long(1);
	
	String gender;
	int division;
	int minAge;
	int maxAge;
	Date time;
	Long eventID;
	
	@Parent
	Key<Heat> parent;
	
	public Heat() {}
	
	




	public Long getId() {
		return id;
	}






	public void setId(Long id) {
		this.id = id;
	}






	public static Long getParentId() {
		return parentId;
	}






	public static void setParentId(Long parentId) {
		Heat.parentId = parentId;
	}






	public String getGender() {
		return gender;
	}






	public void setGender(String gender) {
		this.gender = gender;
	}






	public int getDivision() {
		return division;
	}






	public void setDivision(int division) {
		this.division = division;
	}






	public int getMinAge() {
		return minAge;
	}






	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}






	public int getMaxAge() {
		return maxAge;
	}






	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}






	public Date getTime() {
		return time;
	}






	public void setTime(Date time) {
		this.time = time;
	}






	public Long getEventID() {
		return eventID;
	}






	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}






	public Key<Heat> getParent() {
		return parent;
	}






	public void setParent(Key<Heat> parent) {
		this.parent = parent;
	}






	public Heat(int d, String g, int min, int max, Long e, Date t){
		division = d;
		minAge = min;
		maxAge = max;
		eventID = e;
		time = t;	
	}
}
