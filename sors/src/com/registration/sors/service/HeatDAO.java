//--------------------------------------//
// Name: HeatDAO						//
// Purpose: This class is to provide 	//
//		access to the Heat Datastore.   //
//--------------------------------------//
package com.registration.sors.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Heat;
import com.registration.sors.model.User;
@Service
@SuppressWarnings("javadoc")
public class HeatDAO {
	
	public HeatDAO() {}
	
	@Autowired
	private ObjectifyFactory objectifyFactory;

	// Add a Heat to DataStore
	// Parameters: Heat h - Heat to add
	// Return: Heat - newly added Heat
	
	public Heat add(Heat h){
		try {
			
			h.setParent(new Key<Heat>(Heat.class, Heat.parentId));
			Objectify ofy = this.objectifyFactory.begin();
			ofy.put(h);
			return h;
			
		} catch (Exception e) {
			return null;
		}		
	}
	// Delete a Heat from DataStore
	// Parameters: Heat h - Heat to delete
	// Return: void
	
	public void delete(Heat h){
		Objectify ofy = this.objectifyFactory.begin();
		Heat heat = ofy.get(new Key<Heat>(new Key<Heat>(Heat.class, Heat.parentId), Heat.class, h.getId()));
		ofy.delete(heat);
	}
	// update an Heat in DataStore
	// Parameters: Heat h - Heat to update
	// Return: Heat - newly updated Heat
	
	public void update(Heat h){
		Objectify ofy = this.objectifyFactory.begin();
		
		Heat heat = ofy.get(new Key<Heat>(new Key<Heat>(Heat.class, Heat.parentId), Heat.class, h.getId()));
		
		//fill out this part
		
		ofy.put(heat);
	}
	
	// get a list of Heats in DataStore
	// Parameters: none
	// Return: list of Heats
	
	public List<Heat> loadAll(){
		Objectify ofy = this.objectifyFactory.begin();
		List<Heat> list = new ArrayList<Heat>();
		
		// The following line needs to be executed for every classroomId of every school in the system.
		list.addAll(ofy.query(Heat.class).ancestor(new Key<Heat>(Heat.class,Heat.parentId)).list());
		if(list.size() > 0)
			list.remove(0);
		return list;
	}
	// find a heat whose heatID is id
	// Parameters: id - heatID number
	// Return: heat - whose heatID is id
	
	public Heat find(Long id) {
		try {
			
			Objectify ofy = this.objectifyFactory.begin();
			return ofy.get(new Key<Heat>(new Key<Heat>(Heat.class, Heat.parentId), Heat.class, id));
		
		} catch (Exception e) {
			return null;
		}
	}
	
	public void add(List<Heat> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
	}
	// For Dev ONLY
	public void init() {
		Objectify ofy = this.objectifyFactory.begin();
		Heat parent = new Heat();
		parent.setId(Heat.parentId);
		ofy.put(parent);
		
	}
}
