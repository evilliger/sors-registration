
package com.registration.sors.helpers;

import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;

public class Authenticator {
	
	
	public static boolean authenticate(User u, String pword){
		return (u != null && u.getPword().equals(pword));
	}

	public static boolean isAuthenticated(boolean teacher, boolean admin, SystemSession session){
		return (session != null && session.isAuthenticated() &&
			(session.getUser().getRole().equals("teacher") && teacher ||
			session.getUser().getRole().equals("admin") && admin));
	}
}
