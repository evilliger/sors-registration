//--------------------------------------//
// Name: EventDAO						//
// Purpose: This class is to provide 	//
//		access to the Event Datastore.  //
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

@Service
@SuppressWarnings("javadoc")
public class EventDAO {
	
	@Autowired
	private ObjectifyFactory objectifyFactory;
	
	//public EventDAO() {}
	// Add an Event to DataStore
	// Parameters: Event e - Event to add
	// Return: Event - newly added Event
	
	public Event add(Event e){
		try {
			
			e.setParent(new Key<Event>(Event.class, Event.parentId));
			Objectify ofy = this.objectifyFactory.begin();
			ofy.put(e);
			return e;
			
		} catch (Exception ex) {
			return null;
		}
	}
	// Delete an Event from DataStore
	// Parameters: Event e - Event to delete
	// Return: void
	
	public void delete(Event e){
		Objectify ofy = this.objectifyFactory.begin();
		Event event = ofy.get(new Key<Event>(new Key<Event>(Event.class, Event.parentId), Event.class, e.getId()));
		ofy.delete(event);
	}
	// update an Event in DataStore
	// Parameters: Event e - Event to update
	// Return: Event - newly updated Event
	
	public void update(Event e){
		Objectify ofy = this.objectifyFactory.begin();
		
		Event event = ofy.get(new Key<Event>(new Key<Event>(Event.class, Event.parentId), Event.class, e.getId()));
		
		event.setId(e.getId());
		event.setName(e.getName());
		event.setUnits(e.getUnits());
		event.setMin(e.getMin());
		event.setMax(e.getMax());
		
		ofy.put(event);
	}
	
	// get a list of Events in DataStore
	// Parameters: none
	// Return: list of Events
	
	public List<Event> loadAll(){
		Objectify ofy = this.objectifyFactory.begin();
		List<Event> list = new ArrayList<Event>();
		// The following line needs to be executed for every classroomId of every school in the system.
		list.addAll(ofy.query(Event.class).list());
		if(list.size() > 0)
			list.remove(0);
		return list;
	}
	
	// find an Event whose eventID is id
	// Parameters: id - eventID number
	// Return: event - whose eventID is id
	
	public Event find(Long id){
		try {
			
			Objectify ofy = this.objectifyFactory.begin();
			return ofy.get(new Key<Event>(new Key<Event>(Event.class, Event.parentId), Event.class, id));
		
		} catch (Exception e) {
			return null;
		}
	}
	
	public void add(List<Event> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
	}
}
