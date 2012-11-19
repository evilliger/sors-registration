//--------------------------------------//
// Name: EventConflictDAO				//
// Purpose: This class is to provide 	//
//		access to the EventConflict		//
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
import com.registration.sors.model.Event;
import com.registration.sors.model.EventConflict;

@Service
@SuppressWarnings("javadoc")
public class EventConflictDAO {

	@Autowired
	private ObjectifyFactory objectifyFactory;

	// Add an EventConflict to DataStore
	// Parameters: EventConflict r - EventConflict to add
	// Return: EventConflict - newly added EventConflict
	
	public EventConflict add(EventConflict r){
		try {
			
			r.setParent(new Key<EventConflict>(EventConflict.class, EventConflict.parentId));
			Objectify ofy = this.objectifyFactory.begin();
			ofy.put(r);
			return r;
			
		} catch (Exception e) {
			return null;
		}		
	}
	
	// Delete a EventConflict from DataStore
	// Parameters: EventConflict r - EventConflict to delete
	// Return: void
	
	public void delete(EventConflict r){
		Objectify ofy = this.objectifyFactory.begin();
		EventConflict reg = ofy.get(new Key<EventConflict>(new Key<EventConflict>(EventConflict.class, EventConflict.parentId), EventConflict.class, r.getId()));
		ofy.delete(reg);
	}
	
	// update an EventConflict in DataStore
	// Parameters: EventConflict r - EventConflict to update
	// Return: EventConflict - newly updated EventConflict
	
	public void update(EventConflict r){
		Objectify ofy = this.objectifyFactory.begin();
		
		EventConflict reg = ofy.get(new Key<EventConflict>(new Key<EventConflict>(EventConflict.class, EventConflict.parentId), EventConflict.class, r.getId()));
		
		reg.setE1Id(r.getE1Id());
		reg.setE2Id(r.getE2Id());
		
		ofy.put(reg);
	}
	
	// get a list of EventConflicts in DataStore
	// Parameters: none
	// Return: list of EventConflicts
	
	public List<EventConflict> loadAll(){
		Objectify ofy = this.objectifyFactory.begin();
		List<EventConflict> list = new ArrayList<EventConflict>();
		
		// The following line needs to be executed for every classroomId of every school in the system.
		list.addAll(ofy.query(EventConflict.class).ancestor(new Key<EventConflict>(EventConflict.class,EventConflict.parentId)).list());
		if(list.size() > 0)
			list.remove(0);
		return list;
	}
	
	// find an EventConflict whose regID is id
	// Parameters: id - regID number
	// Return: EventConflict - whose regID is id
	
	public EventConflict find(Long id) {
		try {
			Objectify ofy = this.objectifyFactory.begin();
			return ofy.get(new Key<EventConflict>(new Key<EventConflict>(EventConflict.class, EventConflict.parentId), EventConflict.class, id));
		} catch (Exception e) {
			return null;
		}
	}
	
	// Find a list of event IDs that conflict with <e1>
	public List<Long> find(Event e) {
		try {
			Objectify ofy = this.objectifyFactory.begin();
			List<Long> list = new ArrayList<Long>();
			List<EventConflict> ecs = ofy.query(EventConflict.class).ancestor(new Key<EventConflict>(EventConflict.class,EventConflict.parentId)).filter("e1Id", e.getId()).list();
			for (EventConflict ec : ecs) {
				list.add(ec.getE2Id());
			}
			ecs = ofy.query(EventConflict.class).ancestor(new Key<EventConflict>(EventConflict.class,EventConflict.parentId)).filter("e2Id", e.getId()).list();
			for (EventConflict ec : ecs) {
				list.add(ec.getE1Id());
			}
			return list;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public List<EventConflict> add(List<EventConflict> a) {
		try{
			Objectify ofy = objectifyFactory.begin();
			ofy.put(a);
			return a;
		} catch (Exception e) {
			return null;
		}
	}
}
