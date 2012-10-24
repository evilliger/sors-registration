package com.registration.sors.model;
import java.util.*;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class Athlete {
	
	@Id
	int athleteID;
	String fname;
	String lname;
	char mname;
	Date bdate;
	char gender;
	
    @Parent
    Key<Classroom> classroom;

	public Athlete(){ }
    
	public Athlete(String fn, String ln, Date bday, char g){
		fname = fn;
		lname = ln;
		bdate = bday;
		gender = g;		
	}
	public int getAthleteID() {
		return athleteID;
	}

	public void setAthleteID(int athleteID) {
		this.athleteID = athleteID;
	}
	public String getFname() {
		return fname;
	}

	public void setFname(String firstName) {
		this.fname = firstName;
	}
	public String getLname() {
		return lname;
	}

	public void setLname(String lastName) {
		this.lname = lastName;
	}
	public char getMname() {
		return mname;
	}

	public void setMname(char middleInitial) {
		this.mname = middleInitial;
	}
	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date birthDate) {
		this.bdate = birthDate;
	}
	
	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}
	
	
	
	
}
