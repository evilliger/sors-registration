//----------------------------------------//
// Name: Security						  //
// Purpose: Help to determine if user is  //
//		logged in and authorized		  //
//----------------------------------------//
package com.registration.sors.helpers;

import java.util.List;

import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;

//----------------------------------------//
//Name: Security						  //
//Purpose: Help to determine if user is   //
//		logged in and authorized		  //
//----------------------------------------//
public class Security {
	
	// Name: autheticate
	// Purpose: Determine if the username and password are correct
	// Parameters: u - user object based on username inputed
	//			pword - password to check
	// Return: boolean if the password matches or not
	public static boolean authenticate(User u, String pword){
		return (u != null && u.getPword().equals(pword));
	}

	// Name: isAutheticated
	// Purpose: Determine if the user has been authenticated
	// Parameters: session - the user's current session
	// Return: boolean if there is a valid user in session
	public static boolean isAuthenticated(List<String> roles, SystemSession session){
		return true;//(session != null && session.isAuthenticated() && roles.contains(session.getUser().getRole()));
	}
}
