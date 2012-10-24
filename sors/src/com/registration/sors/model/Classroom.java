package com.registration.sors.model;

@SuppressWarnings("javadoc")
public class Classroom {
	
	int classID;
	String className;
	int schoolID;
	
	public Classroom(String n, int sID){
		className = n;
		schoolID = sID;
	}
	public int getClassID() {
		return classID;
	}

	public void setClassID(int classID) {
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
