//---------------------------------------//
// Name: HeatSpec						 //
// Purpose: This class is to provide 	 //
//		a  HeatSpec object with the same //
// 		attributes as the HeatSpec entity//
//		in the datastore.				 //
//---------------------------------------//
package com.registration.sors.model;
import java.io.Serializable;
import java.util.*;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class HeatSpec implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	Long id;
	
	String gender;
	int minAge;
	int maxAge;
	Date time;
	int numHeats;
	int maxInHeat;
	Long eventId;
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



	public Long getEventId() {
		return eventId;
	}



	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}



	public static Long getParentId() {
		return parentId;
	}



	public static void setParentId(Long parentId) {
		HeatSpec.parentId = parentId;
	}



	public Key<HeatSpec> getParent() {
		return parent;
	}



	public void setParent(Key<HeatSpec> parent) {
		this.parent = parent;
	}



	public HeatSpec(String g, int minA, int maxA, Date t, int num, int max, Long e){
		gender = g;
		minAge = minA;
		maxAge = maxA;
		time = t;
		numHeats = num;
		maxInHeat = max;
		eventId = e;
	}
}
