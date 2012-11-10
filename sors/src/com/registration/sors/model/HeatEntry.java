//--------------------------------------//
// Name: HeatEntry						//
// Purpose: This is a helper class for 	//
//		maintainheatshandler.           //
//--------------------------------------//
package com.registration.sors.model;
import java.util.*;

import javax.persistence.Id;
public class HeatEntry {
	
	Long eventID;
	Long athID;
	Long regID;
	Long heatSpecID;
	
	public HeatEntry() {}
	



	public Long getEventID() {
		return eventID;
	}



	public void setEventID(Long eventID) {
		this.eventID = eventID;
	}




	public Long getRegID() {
		return regID;
	}



	public void setRegID(Long regID) {
		this.regID = regID;
	}
	public Long getAthID() {
		return athID;
	}



	public void setAthID(Long athID) {
		this.athID = athID;
	}
	
	public Long getHeatSpecID() {
		return heatSpecID;
	}



	public void setHeatSpecID(Long heatSpecID) {
		this.heatSpecID = heatSpecID;
	}
	

	public HeatEntry(Long eventID, Long regID, Long athID, Long heatSpecID){
		this.eventID = eventID;
		this.regID = regID;
		this.athID = athID;
		this.heatSpecID = heatSpecID;
	}
}
