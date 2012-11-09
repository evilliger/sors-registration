//--------------------------------------//
// Name: ClassroomDAO					//
// Purpose: This class is to provide 	//
//		access to the Classroom			//
//		datastore.						//
//--------------------------------------//
package com.registration.sors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.School;


@Service
public class ClassroomDAO {
	@Autowired
	private ObjectifyFactory objectifyFactory;
	
	public ClassroomDAO() {}
	
	// init 
	// to initialize the dao
	
	public void init(){
		Objectify ofy = objectifyFactory.begin();
		School sc = new School();
		sc.setId(new Long(1));
		ofy.put(sc);
	}
	
	// Add classroom to DataStore
	// Parameters: classroom c - classroom to add
	// Return: classroom - newly added classroom
	
	public Classroom add(Classroom c){
		try {
			c.setSchool(new Key<School>(School.class, 1));
			Objectify ofy = objectifyFactory.begin();
			ofy.put(c);
			
			return c;
		} catch (Exception e) {
			return null;
		}
	}
	// Delete an Classroom from DataStore
	// Parameters: Classroom c - Classroom to delete
	// Return: void
	
	public void delete(Classroom c){
		Objectify ofy = objectifyFactory.begin();
		Classroom clas = ofy.get(new Key<Classroom>(new Key<School>(School.class, 1), Classroom.class, c.getId()));
		ofy.delete(clas);
	}
	// update a Classroom in DataStore
	// Parameters: Classroom c - Classroom to update
	// Return: Classroom - newly updated Classroom
	
	public void update(Classroom c){
		Objectify ofy = objectifyFactory.begin();

		Classroom newClass = ofy.get(new Key<Classroom>(new Key<School>(School.class, 1), Classroom.class, c.getId()));
		
		newClass.setClassName(c.getClassName());
		newClass.setSchoolID(c.getSchoolID());
		
		ofy.put(newClass);
	}
	
	// get a list of Classrooms in DataStore
	// Parameters: none
	// Return: list of Classrooms
	
	public List<Classroom> loadAll(){
		Objectify ofy = objectifyFactory.begin();
		List<Classroom> list = ofy.query(Classroom.class).ancestor(new Key<School>(School.class,1)).list();
		list.remove(0);
		return list;
	}
	
	// find an classroom whose classID is id
	// Parameters: id - classID number
	// Return: classroom - whose classID is id
	
	public Classroom find(int id){
		try {
			
			Objectify ofy = objectifyFactory.begin();
			return ofy.get(new Key<Classroom>(new Key<School>(School.class, 1), Classroom.class, id));
		} catch (Exception e) {
			return null;
		}
	}
	
	public void add(List<Classroom> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
}
}
