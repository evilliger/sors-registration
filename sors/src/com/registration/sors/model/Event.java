//--------------------------------------//
// Name: Event   						//
// Purpose: This class is to provide 	//
//		an event object with the same   //
// 		attributes as the event entity  //
//		in the datastore.				//
//--------------------------------------//
package com.registration.sors.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("javadoc")
@Entity
public class Event {
	
	@Id
	long eventID;
	String eventName;
	double scoreMin;
	double scoreMax;
	String scoreUnits;
	
	public Event() {}

	public long getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public double getScoreMin() {
		return scoreMin;
	}

	public void setScoreMin(double scoreMin) {
		this.scoreMin = scoreMin;
	}

	public double getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(double scoreMax) {
		this.scoreMax = scoreMax;
	}

	public String getScoreUnits() {
		return scoreUnits;
	}

	public void setScoreUnits(String scoreUnits) {
		this.scoreUnits = scoreUnits;
	}

	public Event(String n, String su){
		eventName = n;
		scoreUnits = su;
	}
}
