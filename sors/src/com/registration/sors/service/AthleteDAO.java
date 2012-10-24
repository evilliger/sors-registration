package com.registration.sors.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.googlecode.objectify.*;

@SuppressWarnings("javadoc")
@Service
public class AthleteDAO {

	@Autowired
	private ObjectifyFactory objectifyFactory;
	
	public void init(){
		Objectify ofy = objectifyFactory.begin();
		Classroom cr = new Classroom();
		cr.setClassID(1);
		ofy.put(cr);
	}
	
	// Add an Athlete to DataStore
	// Parameters: Athlete a - athlete to add
	// Return: Athlete - newly added athlete
	public static Athlete add(Athlete a){
		return null;
	}
	// Delete an Athlete from DataStore
	// Parameters: Athlete a - athlete to delete
	// Return: void
	public static void delete(Athlete a){
	
	}
	// update an Athlete in DataStore
	// Parameters: Athlete a - athlete to update
	// Return: Athlete - newly updated athlete
	public static Athlete update(Athlete a){
		return null;
	}
	
	// get a list of Athletes in DataStore
	// Parameters: none
	// Return: list of Athletes
	public static List<Athlete> loadAll(){
		return null;
	}
	
}
