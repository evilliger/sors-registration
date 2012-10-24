package com.registration.sors.model;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class Contact {
	
	@Id
	public Long id;
	
    private String name;
	
    private String email;
	
    private Date date;
    
    @Parent
    Key<addressBook> Book;
    
    public Long getId() {
    	return id;
    }
    
    public Key<addressBook> getBook() {
		return Book;
	}

	public void setBook(Key<addressBook> book) {
		Book = book;
	}

	public void setId(Long id) {
    	this.id = id;
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Contact() {
		super();
	}	
}