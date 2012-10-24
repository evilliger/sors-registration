package com.registration.sors.model;

@SuppressWarnings("javadoc")
public class Registration {
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
