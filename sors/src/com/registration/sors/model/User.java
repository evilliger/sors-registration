package com.registration.sors.model;

@SuppressWarnings("javadoc")
public class User {
	String title;
	String firstName;
	String lastName;
	String fax;
	String email;
	String password;
	char role;
	char active;
	
	public User(String fn,String ln, String pass, char r, char a){
		firstName = fn;
		lastName = ln;
		password = pass;
		role = r;
		active = a;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char getRole() {
		return role;
	}

	public void setRole(char role) {
		this.role = role;
	}

	public char getActive() {
		return active;
	}

	public void setActive(char active) {
		this.active = active;
	}
}
