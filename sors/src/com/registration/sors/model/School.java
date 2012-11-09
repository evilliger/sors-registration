//--------------------------------------//
// Name: School   				        //
// Purpose: This class is to provide 	//
//		a school object with the        //
// 		same attributes as the 			//
//      school entity in the datastore.	//
//--------------------------------------//
package com.registration.sors.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class School {
	
	@Id
	Long id;
	public static Long parentId = new Long(1);
	String name;
	String groupID;
	int volunteerNum;
	@Parent
	Key<School> parent;

	public School(){}
	
	public int getVolunteerNum() {
		return volunteerNum;
	}
	public void setVolunteerNum(int volunteerNum) {
		this.volunteerNum = volunteerNum;
	}
	
	public Key<School> getParent() {
		return parent;
	}

	public void setParent(Key<School> parent) {
		this.parent = parent;
	}
	
	public School(String gc, String sn){
		name = sn;
		groupID = gc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long long1) {
		this.id = long1;
	}

	public String getName() {
		return name;
	}

	public void setName(String schoolName) {
		this.name = schoolName;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
}
