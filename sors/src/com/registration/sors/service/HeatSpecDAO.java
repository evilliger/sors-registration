//--------------------------------------//
// Name: HeatSpecDAO	    			//
// Purpose: This class is to provide 	//
//		access to the HeatSpec Datastore.//
//--------------------------------------//
package com.registration.sors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.HeatSpec;

@SuppressWarnings("javadoc")
public class HeatSpecDAO {
	
	@Autowired
	private ObjectifyFactory objectifyFactory;
	
	// Add a HeatSpec to DataStore
	// Parameters: HeatSpec h - HeatSpec to add
	// Return: HeatSpec - newly added HeatSpec
	public static HeatSpec add(HeatSpec h){
		return null;
	}
	// Delete a HeatSpec from DataStore
	// Parameters: HeatSpec h - HeatSpec to delete
	// Return: void
	public static void delete(HeatSpec h){
	
	}
	// update a HeatSpec in DataStore
	// Parameters: HeatSpec h - HeatSpec to update
	// Return: HeatSpec - newly updated HeatSpec
	public static HeatSpec update(HeatSpec h){
		return null;
	}
	
	// get a list of HeatSpecs in DataStore
	// Parameters: none
	// Return: list of HeatSpecs
	public static List<HeatSpec> loadAll(){
		return null;
	}
	// find a heatspec whose classID is id
	// Parameters: id - heatspecID number
	// Return: heatspec - whose heatspecID is id
	public static HeatSpec find(int id){
		return null;
	}
	
	public void add(List<HeatSpec> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
}
}
