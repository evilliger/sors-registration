//--------------------------------------//
// Name: Heat   						//
// Purpose: This class is to provide 	//
//		a  heat object with the same    //
// 		attributes as the event entity  //
//		in the datastore.				//
//--------------------------------------//
package com.registration.sors.model;
import java.util.*;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("javadoc")
@Entity
public class Heat {
	@Id
	int heatID;
	String gender;
	int division;
	int minAge;
	int maxAge;
	Date time;
	int eventID;
	
	public int getHeatID() {
		return heatID;
	}

	public void setHeatID(int headID) {
		this.heatID = headID;
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

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public Heat(int d, String g, int min, int max, int e, Date t){
		division = d;
		minAge = min;
		maxAge = max;
		eventID = e;
		time = t;	
	}
}
