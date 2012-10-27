//--------------------------------------//
// Name: UserDAO						//
// Purpose: This class is to provide 	//
//		access to the User Datastore.   //
//--------------------------------------//
package com.registration.sors.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.User;

@SuppressWarnings("javadoc")
@Service
public class UserDAO {
	
	@Autowired
	private ObjectifyFactory objectifyFactory;
	
	// Add an User to DataStore
	// Parameters: User u - User to add
	// Return: User - newly added User
	
	public User add(User u){
		try {
			
			u.setParent(new Key<User>(User.class, User.parentId));
			Objectify ofy = this.objectifyFactory.begin();
			ofy.put(u);
			return u;
			
		} catch (Exception e) {
			return null;
		}
		
	}
	
	// Delete an User from DataStore
	// Parameters: User u - User to delete
	// Return: void
	
	public void delete(User u){
		Objectify ofy = this.objectifyFactory.begin();
		User user = ofy.get(new Key<User>(new Key<User>(User.class, User.parentId), User.class, u.getId()));
		ofy.delete(user);
	}
	
	// update an User in DataStore
	// Parameters: User u - User to update
	// Return: User - newly updated User
	
	public void update(User u){
		Objectify ofy = this.objectifyFactory.begin();
		
		User user = ofy.get(new Key<User>(new Key<User>(User.class, User.parentId), User.class, u.getId()));
		
		user.setTitle(u.getTitle());
		user.setFname(u.getFname());
		user.setLname(u.getLname());
		user.setFax(u.getFax());
		user.setEmail(u.getEmail());
		user.setPword(u.getPword());
		user.setRole(u.getRole());
		user.setActive(u.getActive());
		
		ofy.put(user);
	}
	
	// get a list of Users in DataStore
	// Parameters: none
	// Return: list of Users
	
	public List<User> loadAll(){
		Objectify ofy = this.objectifyFactory.begin();
		List<User> list = new ArrayList<User>();
		
		// The following line needs to be executed for every classroomId of every school in the system.
		list.addAll(ofy.query(User.class).ancestor(new Key<User>(User.class,User.parentId)).list());
		return list;
	}

	// Returns a User for a given id
	
	public User find(Long id) {
		try {
			
			Objectify ofy = this.objectifyFactory.begin();
			return ofy.get(new Key<User>(new Key<User>(User.class, User.parentId), User.class, id));
		
		} catch (Exception e) {
			return null;
		}
	}
	
	// Returns a User for a given username
	// Check this method with Josh and see if it will work
	
	public User find(String email) {
		try {
			Objectify ofy = this.objectifyFactory.begin();
			User u = ofy.query(User.class).ancestor(new Key<User>(User.class, User.parentId)).filter("email", email).get();
			return u;
		} catch (Exception e) {
			return null;
		}
	}
	
	// For Dev ONLY
	public void init() {
		Objectify ofy = this.objectifyFactory.begin();
		User u = new User();
		u.setRole("A");
		u.setActive("T");
		u.setLname("Schaub");
		u.setFname("Steven");
		u.setEmail("Schaub");
		u.setPword("StevieIsTheBomb");
		u.setId(User.parentId);
		ofy.put(u);
	}
	
	public void add(List<User> a) {
		Objectify ofy = objectifyFactory.begin();
		ofy.put(a);
}
}
