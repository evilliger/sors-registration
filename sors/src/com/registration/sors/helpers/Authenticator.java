
package com.registration.sors.helpers;

import java.util.List;

import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;

public class Authenticator {
	
	public static boolean authenticate(User u, String pword){
		return (u != null && u.getPword().equals(pword));
	}

	public static boolean isAuthenticated(List<String> roles, SystemSession session){
		return true;//(session != null && session.isAuthenticated() && roles.contains(session.getUser().getRole()));
	}
}
