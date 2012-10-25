package com.registration.sors.model;

import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Parent;

@SuppressWarnings("javadoc")
@Entity
public class User {
	
	@Id
	Long id;
	
	String title;
	String fname;
	String lname;
	String fax;
	String email;
	String pword;
	String role;
	String active;
	
	@Parent
	Key<SorsParent> sors;

	public User(){ }

	public User(String fn,String ln, String pass, String r, String a){
		this.fname = fn;
		this.lname = ln;
		this.pword = pass;
		this.role = r;
		this.active = a;
	}
	
	public Key<SorsParent> getSors() {
		return sors;
	}

	public void setSors(Key<SorsParent> sors) {
		this.sors = sors;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String firstName) {
		this.fname = firstName;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lastName) {
		this.lname = lastName;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPword() {
		return pword;
	}

	public void setPword(String password) {
		this.pword = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
