package com.registration.sors.service;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.Contact;
import com.registration.sors.model.addressBook;
import com.googlecode.objectify.*;

@SuppressWarnings("javadoc")
@Service
public class AthleteDAO {

	@Autowired
	private ObjectifyFactory objectifyFactory;
	
	public void init(){
		Objectify ofy = objectifyFactory.begin();
		Classroom cr = new Classroom();
		cr.setClassID(new Long(1));
		ofy.put(cr);
	}
	
	// Add an Athlete to DataStore
	// Parameters: Athlete a - athlete to add
	// Return: Athlete - newly added athlete
	public Athlete add(Athlete a){
		try {
			a.setClassroom(new Key<Classroom>(Classroom.class, 1));
			Objectify ofy = objectifyFactory.begin();
			ofy.put(a);
			
			return a;
		} catch (Exception e) {
			return null;
		}
	}
	// Delete an Athlete from DataStore
	// Parameters: Athlete a - athlete to delete
	// Return: void
	public void delete(Athlete a){
		Objectify ofy = objectifyFactory.begin();
		Athlete ath = ofy.get(new Key<Athlete>(new Key<Classroom>(Classroom.class, 1), Athlete.class, a.getId()));
		ofy.delete(ath);
	}
	// update an Athlete in DataStore
	// Parameters: Athlete a - athlete to update
	// Return: Athlete - newly updated athlete
	public void update(Athlete a){
		Objectify ofy = objectifyFactory.begin();

		Athlete newAthlete = ofy.get(new Key<Athlete>(new Key<Classroom>(Classroom.class, 1), Athlete.class, a.getId()));
		
		newAthlete.setFname(a.getFname());
		newAthlete.setLname(a.getLname());
		newAthlete.setMname(a.getMname());
		newAthlete.setBdate(a.getBdate());
		newAthlete.setGender(a.getGender());
		
		ofy.put(newAthlete);
	}
	
	// get a list of Athletes in DataStore
	// Parameters: none
	// Return: list of Athletes
	public List<Athlete> loadAll(){
		Objectify ofy = objectifyFactory.begin();
		List<Athlete> list = ofy.query(Athlete.class).ancestor(new Key<Classroom>(Classroom.class,1)).list();
		return list;
	}
	
}
