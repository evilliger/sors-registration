//--------------------------------------//
// Name: EventDAO						//
// Purpose: This class is to provide 	//
//		access to the Event Datastore.  //
//--------------------------------------//
package com.registration.sors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Event;

@SuppressWarnings("javadoc")
public class EventDAO {
	
	@Autowired
	private ObjectifyFactory objectifyFactory;

	// Add an Event to DataStore
	// Parameters: Event e - Event to add
	// Return: Event - newly added Event
	
	public static Event add(Event e){
		return null;
	}
	// Delete an Event from DataStore
	// Parameters: Event e - Event to delete
	// Return: void
	
	public static void delete(Event e){
	
	}
	// update an Event in DataStore
	// Parameters: Event e - Event to update
	// Return: Event - newly updated Event
	
	public static Event update(Event e){
		return null;
	}
	
	// get a list of Events in DataStore
	// Parameters: none
	// Return: list of Events
	
	public static List<Event> loadAll(){
		return null;
	}
	
	// find an Event whose eventID is id
	// Parameters: id - eventID number
	// Return: event - whose eventID is id
	
	public static Event find(int id){
		return null;
	}
	
	public void add(List<Event> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
}
}
