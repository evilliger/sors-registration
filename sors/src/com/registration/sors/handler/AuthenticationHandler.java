package com.registration.sors.handler;

import com.registration.sors.model.User;
import com.registration.sors.service.UserDAO;

public class AuthenticationHandler {

	public User Authenticate(String username, String pass){
		UserDAO dal = new UserDAO();
		User u = dal.findUsername(username);
		if(u == null){
			return null;
		}else{
			if(u.getPword().equals(pass)){
				return u;
			}else{
				return null;
			}
		}
	}
}
