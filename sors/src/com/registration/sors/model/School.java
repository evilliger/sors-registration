//--------------------------------------//
// Name: School   				        //
// Purpose: This class is to provide 	//
//		a school object with the        //
// 		same attributes as the 			//
//      school entity in the datastore.	//
//--------------------------------------//
package com.registration.sors.model;

@SuppressWarnings("javadoc")
public class School {
	Long schoolID;
	String schoolName;
	String groupID;
	public School(){}
	public School(String gc, String sn){
		schoolName = sn;
		groupID = gc;
	}

	public Long getSchoolID() {
		return schoolID;
	}

	public void setSchoolID(Long long1) {
		this.schoolID = long1;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getGroupID() {
		return groupID;
	}

	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
}
