//--------------------------------------//
// Name: RegistrationDAO				//
// Purpose: This class is to provide 	//
//		access to the Registration		//
//		Datastore.                      //
//--------------------------------------//
package com.registration.sors.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
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
	
	public Registration add(Registration r){
		try {
			
			r.setParent(new Key<Registration>(Registration.class, Registration.parentId));
			Objectify ofy = this.objectifyFactory.begin();
			ofy.put(r);
			return r;
			
		} catch (Exception e) {
			return null;
		}		
	}
	
	// Delete a Registration from DataStore
	// Parameters: Registration r - Registration to delete
	// Return: void
	
	public void delete(Registration r){
		Objectify ofy = this.objectifyFactory.begin();
		Registration reg = ofy.get(new Key<Registration>(new Key<Registration>(Registration.class, Registration.parentId), Registration.class, r.getId()));
		ofy.delete(reg);
	}
	
	// update an Registration in DataStore
	// Parameters: Registration r - Registration to update
	// Return: Registration - newly updated Registration
	
	public void update(Registration r){
		Objectify ofy = this.objectifyFactory.begin();
		
		Registration reg = ofy.get(new Key<Registration>(new Key<Registration>(Registration.class, Registration.parentId), Registration.class, r.getId()));
		
		reg.setAthleteID(r.getAthleteID());
		reg.setEventID(r.getEventID());
		reg.setRank(r.getRank());
		reg.setScore(r.getScore());
		reg.setHeatID(r.getHeatID());
		
		ofy.put(reg);
	}
	
	// get a list of Registrations in DataStore
	// Parameters: none
	// Return: list of Registrations
	
	public List<Registration> loadAll(){
		Objectify ofy = this.objectifyFactory.begin();
		List<Registration> list = new ArrayList<Registration>();
		
		// The following line needs to be executed for every classroomId of every school in the system.
		list.addAll(ofy.query(Registration.class).ancestor(new Key<Registration>(Registration.class,Registration.parentId)).list());
		if(list.size() > 0)
			list.remove(0);
		return list;
	}
	
	// find an Registration whose regID is id
	// Parameters: id - regID number
	// Return: registration - whose regID is id
	
	public Registration find(Long id) {
		try {
			
			Objectify ofy = this.objectifyFactory.begin();
			return ofy.get(new Key<Registration>(new Key<Registration>(Registration.class, Registration.parentId), Registration.class, id));
		
		} catch (Exception e) {
			return null;
		}
	}
	public void add(List<Registration> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
}
}
