//--------------------------------------//
// Name: SchoolDAO						//
// Purpose: This class is to provide 	//
//		access to the School Datastore. //
//--------------------------------------//
package com.registration.sors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.School;
@Service
@SuppressWarnings("javadoc")
public class SchoolDAO {

	@Autowired
	private ObjectifyFactory objectifyFactory;
	// Add an School to DataStore
	// Parameters: School s - School to add
	// Return: School - newly added School
	
	public static School add(School s){
		return null;
	}
	
	// Delete an School from DataStore
	// Parameters: School s - School to delete
	// Return: void
	
	public static void delete(School s){
	
	}
	
	// update an School in DataStore
	// Parameters: School s - School to update
	// Return: School - newly updated School
	
	public static School update(School s){
		return null;
	}
	
	// get a list of Schools in DataStore
	// Parameters: none
	// Return: list of Schools
	
	public static List<School> loadAll(){
		return null;
	}
	
	// find an School whose schoolID is id
	// Parameters: id - schoolID number
	// Return: school - whose schoolID is id
	
	public static School find(int id){
		return null;
	}
	
	public void add(List<School> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
}
}
