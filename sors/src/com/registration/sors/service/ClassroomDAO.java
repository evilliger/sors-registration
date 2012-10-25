//--------------------------------------//
// Name: ClassroomDAO					//
// Purpose: This class is to provide 	//
//		access to the Classroom			//
//		datastore.						//
//--------------------------------------//
package com.registration.sors.service;

import java.util.List;

import com.registration.sors.model.Classroom;

@SuppressWarnings("javadoc")
public class ClassroomDAO {
	// Add classroom to DataStore
	// Parameters: classroom c - classroom to add
	// Return: classroom - newly added classroom
	public static Classroom add(Classroom c){
		return null;
	}
	// Delete an Classroom from DataStore
	// Parameters: Classroom c - Classroom to delete
	// Return: void
	public static void delete(Classroom c){
	
	}
	// update a Classroom in DataStore
	// Parameters: Classroom c - Classroom to update
	// Return: Classroom - newly updated Classroom
	public static Classroom update(Classroom c){
		return null;
	}
	
	// get a list of Classrooms in DataStore
	// Parameters: none
	// Return: list of Classrooms
	public static List<Classroom> loadAll(){
		return null;
	}
	
	// find an classroom whose classID is id
	// Parameters: id - classID number
	// Return: classroom - whose classID is id
	public static Classroom find(int id){
		return null;
	}
}
