//--------------------------------------//
// Name: Athlete						//
// Purpose: This class is to provide 	//
//		an athlete object with the same //
// 		attributes as the athlete entity//
//		in the datastore.				//
//--------------------------------------//
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
	Long Id;
	String fname;
	String lname;
	String mname;
	Date bdate;
	String gender;
	
    @Parent
    Key<Classroom> classroom;

	public Athlete(){ }
    
	public Athlete(String fn, String ln, Date bday, String g){
		fname = fn;
		lname = ln;
		bdate = bday;
		gender = g;		
	}

	public Key<Classroom> getClassroom() {
		return classroom;
	}

	public void setClassroom(Key<Classroom> classroom) {
		this.classroom = classroom;
	}
	
	public Long getId() {
		return Id;
	}

	public void setId(Long athleteID) {
		this.Id = athleteID;
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
	public String getMname() {
		return mname;
	}

	public void setMname(String middleInitial) {
		this.mname = middleInitial;
	}
	public Date getBdate() {
		return bdate;
	}

	public void setBdate(Date birthDate) {
		this.bdate = birthDate;
	}
	
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
	
	
}
