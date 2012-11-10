//--------------------------------------//
// Name: Classroom						//
// Purpose: This class is to provide 	//
//		a classroom object with the same//
// 		attributes as the classroom     //
//		entity in the datastore.	    //
//--------------------------------------//
package com.registration.sors.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class Classroom {
	
	@Id
	Long id;
	String className;
	Long schoolID;
	Long userID;
	@Parent
    Key<School> school;
	public Classroom(){ }
	
	public Classroom(String n, Long sID){
		className = n;
		schoolID = sID;
	}
	public Key<School> getSchool() {
		return school;
	}

	public void setSchool(Key<School> school) {
		this.school = school;
	}
	public Long getId() {
		return id;
	}
	
	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public Long getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(Long schoolID) {
		this.schoolID = schoolID;
	}
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
