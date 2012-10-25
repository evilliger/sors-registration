//--------------------------------------//
// Name: HeatEntry						//
// Purpose: This is a helper class for 	//
//		maintainheatshandler.           //
//--------------------------------------//
package com.registration.sors.model;
import java.util.*;
public class HeatEntry {
	int eventID;
	int minAge;
	int maxAge;
	Date time;
	String gender;
	int numHeats;
	int maxInHeat;
	double score;
	
	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public HeatEntry(int e,int a1,int a2, Date t, String g, double s){
		eventID = e;
		minAge =a1;
		maxAge = a2;
		time = t;
		gender = g;
		score = s;
	}
}
