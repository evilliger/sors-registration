//---------------------------------------//
// Name: HeatSpec						 //
// Purpose: This class is to provide 	 //
//		a  HeatSpec object with the same //
// 		attributes as the HeatSpec entity//
//		in the datastore.				 //
//---------------------------------------//
package com.registration.sors.model;
import java.util.*;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class HeatSpec {
	
	@Id
	Long id;
	String gender;
	int minAge;
	int maxAge;
	Date time;
	int numHeats;
	int maxInHeat;
	int eventId;
	public static Long parentId = new Long(1);

	@Parent
	Key<HeatSpec> parent;
	
	public HeatSpec() {}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


	public String getGender() {
		return gender;
	}


	public void setGender(String gender) {
		this.gender = gender;
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


	public int getNumHeats() {
		return numHeats;
	}


	public void setNumHeats(int numHeats) {
		this.numHeats = numHeats;
	}


	public int getMaxInHeat() {
		return maxInHeat;
	}


	public void setMaxInHeat(int maxInHeat) {
		this.maxInHeat = maxInHeat;
	}


	public int getEventID() {
		return eventId;
	}


	public void setEventID(int eventID) {
		this.eventId = eventID;
	}


	public Key<HeatSpec> getParent() {
		return parent;
	}


	public void setParent(Key<HeatSpec> parent) {
		this.parent = parent;
	}


	public HeatSpec(String g, int minA, int maxA, Date t, int num, int max, int e){
		gender = g;
		minAge = minA;
		maxAge = maxA;
		time = t;
		numHeats = num;
		maxInHeat = max;
		eventId = e;
	}
}
