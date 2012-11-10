//----------------------------------------//
// Name: Security						  //
// Purpose: Help to determine if user is  //
//		logged in and authorized		  //
//----------------------------------------//
package com.registration.sors.helpers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;

//----------------------------------------//
//Name: Security						  //
//Purpose: Help to determine if user is   //
//		logged in and authorized		  //
//----------------------------------------//
public class Security {
	
	static Logger log = Logger.getLogger(Security.class.getName());
	
	// Name: autheticate
	// Purpose: Determine if the username and password are correct
	// Parameters: u - user object based on username inputed
	//			pword - password to check
	// Return: boolean if the password matches or not
	public static boolean authenticate(User u, String pword){
		boolean result = (u != null && u.getPword() != null && u.getPword().equals(pword));
		if(result && u != null && u.getEmail() != null)
			log.warning(u.getEmail() + " logged in.");
		return result;
	}

	// Name: isAutheticated
	// Purpose: Determine if the user has been authenticated
	// Parameters: session - the user's current session
	// Return: boolean if there is a valid user in session
	public static boolean isAuthenticated(List<String> roles, SystemSession session){
		boolean result = (session != null && session.isAuthenticated() && roles.contains(session.getUser().getRole()));
		User u;
		if(!result && session != null && (u = session.getUser()) != null && u != null && u.getEmail() != null)
			log.warning(u.getEmail() + " tried to access an unauthorized page.");
		return result;
	}
}
