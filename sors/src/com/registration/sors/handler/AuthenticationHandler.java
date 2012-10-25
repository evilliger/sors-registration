package com.registration.sors.handler;

import com.registration.sors.model.User;
import com.registration.sors.service.UserDAO;

public class AuthenticationHandler {

	public boolean Authenticate(String username, String pass){
		UserDAO dal = new UserDAO();
		User u = dal.find(username);
		if(u == null){
			return false;
		}else{
			if(u.getPword().equals(pass)){
				return true;
			}else{
				return false;
			}
		}
	}
}
