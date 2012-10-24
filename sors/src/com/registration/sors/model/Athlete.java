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
	String firstName;
	String lastName;
	char middleInitial;
	Date birthDate;
	char gender;
	
    @Parent
    Key<Classroom> classroom;

	public Athlete(){ }
    
	public Athlete(String fn, String ln, Date bday, char g){
		firstName = fn;
		lastName = ln;
		birthDate = bday;
		gender = g;		
	}
	public int getAthleteID() {
		return athleteID;
	}

	public void setAthleteID(int athleteID) {
		this.athleteID = athleteID;
	}
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public char getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(char middleInitial) {
		this.middleInitial = middleInitial;
	}
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public char getGender() {
		return gender;
	}

	public void setGender(char gender) {
		this.gender = gender;
	}
	
	
	
	
}
