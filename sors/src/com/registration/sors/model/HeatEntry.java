//--------------------------------------//
// Name: HeatEntry						//
// Purpose: This is a helper class for 	//
//		maintainheatshandler.           //
//--------------------------------------//
package com.registration.sors.model;
import java.util.*;

import javax.persistence.Id;
public class HeatEntry {
	
	@Id
	Long id;
	Long eventID;
	int minAge;
	int maxAge;
	Date time;
	String gender;
	int numHeats;
	int maxInHeat;
	double score;
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Long getEventID() {
		return eventID;
	}



	public void setEventID(Long eventID) {
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



	public Long getRegID() {
		return regID;
	}



	public void setRegID(Long regID) {
		this.regID = regID;
	}



	Long regID;
	
	public HeatEntry() {}
	
	

	public HeatEntry(Long e,int a1,int a2, Date t, String g, double s, Long regID){
		eventID = e;
		minAge =a1;
		maxAge = a2;
		time = t;
		gender = g;
		score = s;
		this.regID = regID;
	}
}
