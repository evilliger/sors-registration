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

import com.googlecode.objectify.annotation.Entity;

@SuppressWarnings("javadoc")
@Entity
public class Registration {
	@Id
	int regID;
	double score;
	int rank;
	int athleteID;
	int eventID;
	
	public int getRegID() {
		return regID;
	}

	public void setRegID(int regID) {
		this.regID = regID;
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

	public int getAthleteID() {
		return athleteID;
	}

	public void setAthleteID(int athleteID) {
		this.athleteID = athleteID;
	}

	public int getEventID() {
		return eventID;
	}

	public void setEventID(int eventID) {
		this.eventID = eventID;
	}

	public Registration(int a, int e){
		athleteID = a;
		eventID = e;
	}
}
