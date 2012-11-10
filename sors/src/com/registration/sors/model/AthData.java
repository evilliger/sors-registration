package com.registration.sors.model;

//Class that holds registrations and errors associated with them
public class AthData {
	public Athlete athlete;
	public boolean completed;
	public String school;
	public String event1;
	public String event2;

	public AthData(Athlete athlete, boolean completed, String school,
			String event1, String event2) {
		this.athlete = athlete;
		this.completed = completed;
		this.school = school;
		this.event1 = event1;
		this.event2 = event2;
	}
	
	public Athlete getAthlete() {
		return this.athlete;
	}
	public void setAthlete(Athlete athlete) {
		this.athlete = athlete;
	}
	public boolean isCompleted() {
		return this.completed;
	}
	public void setCompleted(boolean completed) {
		this.completed = completed;
	}
	public String getSchool() {
		return this.school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getEvent1() {
		return this.event1;
	}
	public void setEvent1(String event1) {
		this.event1 = event1;
	}
	public String getEvent2() {
		return this.event2;
	}
	public void setEvent2(String event2) {
		this.event2 = event2;
	}
}
