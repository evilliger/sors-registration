package com.csnyder.service;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csnyder.model.Contact;
import com.csnyder.model.addressBook;
import com.googlecode.objectify.*;
import com.googlecode.objectify.Key;
//import com.google.appengine.api.datastore.*;


@Service
public class ContactDAO {
	
	@Autowired
	private ObjectifyFactory objectifyFactory;
	
	public ContactDAO() { }
	
	// Returns ALL the Contacts
	public List<Contact> LoadAll() {
		Objectify ofy = objectifyFactory.begin();
		List<Contact> c = ofy.query(Contact.class).ancestor(new Key<addressBook>(addressBook.class,1)).list();
		return c;
	}
	
	public  void init(){
		Objectify ofy = objectifyFactory.begin();
		addressBook ab = new addressBook();
		ab.setId(new Long(1));
		ofy.put(ab);
	}
	
	// Adds a Contact to the Datastore
	public Boolean addContact(Contact c) {
		
		
		try {
			c.setDate(new Date());
			c.setBook(new Key<addressBook>(addressBook.class, 1));
			Objectify ofy = objectifyFactory.begin();
			ofy.put(c);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// TODO: Transactionify
	// Updates an existing Contact in the Datastore with the info from contact passed in
	public Boolean UpdateContact(Contact c) {
		try {
			Objectify ofy = objectifyFactory.begin();
			
			Contact fetched1 = ofy.get(Contact.class, c.getId());
			
			fetched1.setDate(new Date());
			fetched1.setName(c.getName());
			fetched1.setEmail(c.getEmail());
			
			ofy.put(fetched1);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// Deletes a contact
	// TODO: Transactionify
	public Boolean DeleteContact(Contact c) {
		try {
			Objectify ofy = objectifyFactory.begin();
			Contact fetched1 = ofy.get(new Key<Contact>(new Key<addressBook>(addressBook.class, 1), Contact.class, c.getId()));
			ofy.delete(fetched1);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	// Returns a Contact for a given id
	public Contact GetContactByID(Long id) {
		try {
			Objectify ofy = objectifyFactory.begin();
			return ofy.get(Contact.class, id);
		} catch (Exception e) {
			return null;
		}
	}
	
	// Searches for a contact based on the supplied name
	public List<Contact> NameSearch(String name) {
		Objectify ofy = objectifyFactory.begin();
		List<Contact> l = ofy.query(Contact.class).filter("name", name).list();
		return l;
	}
}
