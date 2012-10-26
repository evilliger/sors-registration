//--------------------------------------//
// Name: UserDAO						//
// Purpose: This class is to provide 	//
//		access to the User Datastore.   //
//--------------------------------------//
package com.registration.sors.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.registration.sors.model.Contact;
import com.registration.sors.model.SorsParent;
import com.registration.sors.model.User;
import com.registration.sors.model.addressBook;

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
			u.setSors(new Key<SorsParent>(SorsParent.class, 1));
			Objectify ofy = objectifyFactory.begin();
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
		Objectify ofy = objectifyFactory.begin();
		User user = ofy.get(new Key<User>(new Key<SorsParent>(SorsParent.class, 1), User.class, u.getId()));
		ofy.delete(user);
	}
	
	// update an User in DataStore
	// Parameters: User u - User to update
	// Return: User - newly updated User
	public void update(User u){
		Objectify ofy = objectifyFactory.begin();
		
		User user = ofy.get(new Key<User>(new Key<SorsParent>(SorsParent.class, 1), User.class, u.getId()));
		
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
		Objectify ofy = objectifyFactory.begin();
		List<User> list = ofy.query(User.class).ancestor(new Key<SorsParent>(SorsParent.class,1)).list();
		return list;
	}

	// Returns a User for a given id
	public User find(Long id) {
		try {
			Objectify ofy = objectifyFactory.begin();
			return ofy.get(new Key<User>(new Key<SorsParent>(SorsParent.class, 1), User.class, id));
		} catch (Exception e) {
			return null;
		}
	}
	// Returns a User for a given username
	// Check this method with Josh and see if it will work
	public User find(String email) {
		try {
			Objectify ofy = objectifyFactory.begin();
			User u = ofy.query(User.class).ancestor(new Key<SorsParent>(SorsParent.class, 1)).filter("email", email).get();
			return u;
		} catch (Exception e) {
			return null;
		}
	}
	
	public void init() {
		Objectify ofy = objectifyFactory.begin();
		SorsParent sors = new SorsParent();
		sors.setId(new Long(1));
		ofy.put(sors);
	}
}
