package com.registration.sors.model;
import java.util.*;

@SuppressWarnings("javadoc")
public class Heat {
	int headID;
	char gender;
	int division;
	int minAge;
	int maxAge;
	Date time;
	int eventID;
	
	public int getHeadID() {
		return headID;
	}

	public void setHeadID(int headID) {
		this.headID = headID;
	}

	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
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

	public Heat(int d, char g, int min, int max, int e, Date t){
		division = d;
		minAge = min;
		maxAge = max;
		eventID = e;
		time = t;	
	}
}
