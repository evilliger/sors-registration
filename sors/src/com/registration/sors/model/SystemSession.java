//-----------------------------------//
// Name: SystemSession				 //
// Purpose: The object to be stored  //
//		in the session				 //
//-----------------------------------//
package com.registration.sors.model;

@SuppressWarnings("javadoc")
public class SystemSession {
	
	User user;
	boolean authenticated;

	public SystemSession(User user, boolean authenticated) {
		super();
		this.user = user;
		this.authenticated = authenticated;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean isAuthenticated() {
		return this.authenticated;
	}
	
	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}
	
	
}
