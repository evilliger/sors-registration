//---------------------------------------//
// Name: HeatSpec						 //
// Purpose: This class is to provide 	 //
//		a  HeatSpec object with the same //
// 		attributes as the HeatSpec entity//
//		in the datastore.				 //
//---------------------------------------//
package com.registration.sors.model;
import java.util.*;

@SuppressWarnings("javadoc")
public class HeatSpec {
	int HeatSpecID;
	String gender;
	int minAge;
	int maxAge;
	Date time;
	int numHeats;
	int maxInHeat;
	int eventID;
	
	public int getHeatSpecID() {
		return HeatSpecID;
	}

	public void setHeatSpecID(int heatSpecID) {
		HeatSpecID = heatSpecID;
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
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public HeatSpec(char g, int minA, int maxA, Date t, int num, int max, int e){
		gender = g;
		minAge = minA;
		maxAge = maxA;
		time = t;
		numHeats = num;
		maxInHeat = max;
		eventID = e;
	}
}
