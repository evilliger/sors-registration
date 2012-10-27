//--------------------------------------//
// Name: RegistrationDAO				//
// Purpose: This class is to provide 	//
//		access to the Registration		//
//		Datastore.                      //
//--------------------------------------//
package com.registration.sors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Registration;
@Service
@SuppressWarnings("javadoc")
public class RegistrationDAO {

	@Autowired
	private ObjectifyFactory objectifyFactory;
	
	// Add a Registration to DataStore
	// Parameters: Registration r - Registration to add
	// Return: Registration - newly added Registration
	
	public static Registration add(Registration r){
		return null;
	}
	
	// Delete a Registration from DataStore
	// Parameters: Registration r - Registration to delete
	// Return: void
	
	public static void delete(Registration r){
	
	}
	
	// update an Registration in DataStore
	// Parameters: Registration r - Registration to update
	// Return: Registration - newly updated Registration
	
	public static Registration update(Registration r){
		return null;
	}
	
	// get a list of Registrations in DataStore
	// Parameters: none
	// Return: list of Registrations
	
	public static List<Registration> loadAll(){
		return null;
	}
	
	// find an Registration whose regID is id
	// Parameters: id - regID number
	// Return: registration - whose regID is id
	
	public static Registration find(int id){
		return null;
	}
	
	public void add(List<Registration> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
}
}
