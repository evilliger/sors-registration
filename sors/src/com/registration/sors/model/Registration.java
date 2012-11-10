//--------------------------------------//
// Name: Registration   				//
// Purpose: This class is to provide 	//
//		a  Registration object with the //
// 		same attributes as the 			//
//      Registration entity in the 		//
//		datastore.			         	//
//--------------------------------------//
package com.registration.sors.model;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class Registration {
	@Id
	Long id;
	public static Long parentId = new Long(-1);
	
	double score;
	int rank;
	Long athleteID;
	Long eventID;
	Long heatID;
	


	@Parent
	Key<Registration> parent;
	
	public Registration() {}
	
	public Key<Registration> getParent() {
		return parent;
	}

	public void setParent(Key<Registration> parent) {
		this.parent = parent;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Long getAthleteID() {
		return athleteID;
	}

	public void setAthleteID(Long athleteID) {
		this.athleteID = athleteID;
	}

	public Long getEventID() {
		return eventID;
	}

	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}
	public Long getHeatID() {
		return heatID;
	}

	public void setHeatID(Long heatID) {
		this.heatID = heatID;
	}
}
