//--------------------------------------//
// Name: SchoolDAO						//
// Purpose: This class is to provide 	//
//		access to the School Datastore. //
//--------------------------------------//
package com.registration.sors.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.School;
import com.registration.sors.model.User;
@Service
@SuppressWarnings("javadoc")
public class SchoolDAO {

	@Autowired
	private ObjectifyFactory objectifyFactory;
	// Add an School to DataStore
	// Parameters: School s - School to add
	// Return: School - newly added School
	
	public School add(School s){
		try {
			s.setParent(new Key<School>(School.class, School.parentId));
			Objectify ofy = this.objectifyFactory.begin();
			ofy.put(s);
			return s;
			
		} catch (Exception e) {
			return null;
		}		
	}
	
	// Delete an School from DataStore
	// Parameters: School s - School to delete
	// Return: void
	
	public void delete(School s){
	
	}
	
	// update an School in DataStore
	// Parameters: School s - School to update
	// Return: School - newly updated School
	
	public School update(School s){
		return null;
	}
	
	// get a list of Schools in DataStore
	// Parameters: none
	// Return: list of Schools
	
	public List<School> loadAll(){
		Objectify ofy = objectifyFactory.begin();
		List<School> list = new ArrayList<School>();
		
		// The following line needs to be executed for every classroomId of every school in the system.
		list.addAll(ofy.query(School.class).ancestor(new Key<School>(School.class,School.parentId)).list());
		if(list.size() > 0)
			list.remove(0);
		return list;
	}
	
	// find an School whose schoolID is id
	// Parameters: id - schoolID number
	// Return: school - whose schoolID is id
	
	public School find(int id){
		return null;
	}
	
	public void add(List<School> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
}
}
