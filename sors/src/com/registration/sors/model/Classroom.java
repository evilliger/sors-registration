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
	Long classID;
	String className;
	int schoolID;
	@Parent
    Key<School> school;
	public Classroom(){ }
	
	public Classroom(String n, int sID){
		className = n;
		schoolID = sID;
	}
	public Key<School> getSchool() {
		return school;
	}

	public void setSchool(Key<School> school) {
		this.school = school;
	}
	public Long getClassID() {
		return classID;
	}

	public void setClassID(Long classID) {
		this.classID = classID;
	}
	public int getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(int schoolID) {
		this.schoolID = schoolID;
	}
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

}
