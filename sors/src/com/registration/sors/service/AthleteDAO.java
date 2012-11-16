//--------------------------------------//
// Name: AthleteDAO						//
// Purpose: This class is to provide 	//
//		access to the Athlete Datastore.//
//--------------------------------------//
package com.registration.sors.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.User;


@Service
public class AthleteDAO {
	public AthleteDAO(){}

	@Autowired
	private ObjectifyFactory objectifyFactory;
	
	// Add an Athlete to DataStore
	// Parameters: Athlete a - athlete to add
	// Return: Athlete - newly added athlete
	
	public Athlete add(Athlete a) throws Exception{
		a.setClassroom(new Key<Classroom>(Classroom.class, a.getClassroomId()));
		Objectify ofy = this.objectifyFactory.begin();
		ofy.put(a);
		return a;
	}
	// Delete an Athlete from DataStore
	// Parameters: Athlete a - athlete to delete
	// Return: void
	
	public Athlete delete(Athlete a) throws Exception {
		Objectify ofy = this.objectifyFactory.begin();
		Athlete ath = ofy.get(new Key<Athlete>(new Key<Classroom>(Classroom.class, a.getClassroomId()), Athlete.class, a.getId()));
		ofy.delete(ath);
		return ath;
	}
	
	// update an Athlete in DataStore
	// Parameters: Athlete a - athlete to update
	// Return: Athlete - newly updated athlete
	
	public void update(Athlete a){
		Objectify ofy = this.objectifyFactory.begin();

		Athlete newAthlete = ofy.get(new Key<Athlete>(new Key<Classroom>(Classroom.class, a.getClassroomId()), Athlete.class, a.getId()));
		
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
	
	public List<Athlete> loadAll(User u){
		Objectify ofy = this.objectifyFactory.begin();
		List<Athlete> list = new ArrayList<Athlete>();
		
		// The following line needs to be executed for every classroomId of every school in the system instead of "1".
		if (u.getRole().equals("T")) {
			Classroom c = ofy.query(Classroom.class).filter("userID", u.getId()).get();
			list.addAll(ofy.query(Athlete.class).ancestor(new Key<Classroom>(Classroom.class,c.getId())).list());
		}
		return list;
	}
	// get a list of Athletes in DataStore
	// Parameters: none
	// Return: list of Athletes
	
	public List<Athlete> loadAll(){
		Objectify ofy = this.objectifyFactory.begin();
		List<Athlete> list = new ArrayList<Athlete>();
		list =  ofy.query(Athlete.class).list();
		return list;
	}
	
	// find an athlete whose athleteID is id and whose classrom is c
	public Athlete find(Long id, Classroom c) throws Exception{
		Objectify ofy = this.objectifyFactory.begin();

		return ofy.get(new Key<Athlete>(new Key<Classroom>(Classroom.class, c.getId()), Athlete.class, id));
	}
	
	public void add(List<Athlete> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
	}
	
}
