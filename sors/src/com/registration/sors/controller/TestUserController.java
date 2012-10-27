//------------------------------------------------//
// Name:TestUserController		    			  //
// Purpose: To test the methods in the userdao    //
//		class									  //
//------------------------------------------------//
package com.registration.sors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.registration.sors.model.User;
import com.registration.sors.service.UserDAO;

@Controller
@RequestMapping("/")
//------------------------------------------------//
//Name:TestUserController		    			  //
//Purpose: To test the methods in the userdao    //
//		class									  //
//------------------------------------------------//
public class TestUserController {
	
	@Autowired private UserDAO dao;

	// Name: test
	// Parameters: none
	// Returns a page to display.
	//		passed.jup if all tests pass
	//		failed.jup if any test fails
	@RequestMapping(value = "/testuser", method = RequestMethod.GET)
	public ModelAndView test() {
		
		boolean allPass = true;
		int numAthletes = this.dao.loadAll().size();
		
		
		//---------------------------------------//
		//				ADD USER				 //
		//---------------------------------------//
		User a = new User();
		a.setFname("Max");
		this.dao.add(a);
		allPass = (numAthletes + 1 == this.dao.loadAll().size()) & allPass; //) : "Add User Unit Test 1 size";
		allPass =  (this.dao.find(a.getId()).getFname().equals("Max")) & allPass;//: "Add User Unit Test 2 return";
		this.dao.delete(a); // clean up
		allPass =  (numAthletes == this.dao.loadAll().size()) & allPass; // : "Add User Unit Test 3 delete";
		
		User b = new User();
		b.setFname("Billy");
		b.setLname("Joel");
		b.setEmail("jay@gmail.com");
		this.dao.add(b);
		
		User c = new User();
		c.setFname("Max");
		c.setEmail("Max@gmail.com");
		this.dao.add(c);
		
		User d = new User();
		d.setFname("Michael");
		this.dao.add(d);
		
		User e = new User();
		e.setFname("Kimberly");
		this.dao.add(e);
		
		
		
		allPass = (numAthletes + 4 == this.dao.loadAll().size()) & allPass; // : "Add User Unit Test 4 size (multiple adds)";
		allPass = (this.dao.find("Beth") == null) & allPass;// : "Add User Unit Test 5 object not in datastore";
		
		//---------------------------------------//
		//				FIND USER				 //
		//---------------------------------------//
		
		allPass = (this.dao.find(d.getId()).getFname().equals("Michael")) & allPass;//  : "Find User Unit Test 2 object in datastore";
		
		//---------------------------------------//
		//				FIND2 USER				 //
		//---------------------------------------//
		
		allPass =(this.dao.find("jay@gmail.com").getLname().equals("Joel")) & allPass;// : "Find2 User Unit Test 1 object in datastore";
		
		
		//---------------------------------------//
		//				Update USER				 //
		//---------------------------------------//
		
		allPass = (this.dao.find(e.getId()).getLname() == null) & allPass;// : "Find2 User Unit Test 1 object in datastore";
		
		e.setEmail("kberry@juno.com");
		e.setLname("Smith");
		
		this.dao.update(e);
		allPass =(this.dao.find(e.getId()).getLname().equals("Smith")) & allPass;// : "Find2 User Unit Test 1 object in datastore";
		
		
		//---------------------------------------//
		//				Delete USER				 //
		//---------------------------------------//
		this.dao.delete(b); // clean up
		this.dao.delete(c); // clean up
		allPass = (this.dao.loadAll().size() == numAthletes + 2)& allPass ;//: "Delete User Unit Test 1";
		allPass = (this.dao.find(b.getId()) == null)& allPass;// : "Delete User Unit Test 2 (make sure billy is really gone";
		
		this.dao.delete(d); // clean up
		this.dao.delete(e); // clean up
		
		allPass = (this.dao.loadAll().size() == numAthletes) & allPass;//: "Delete User Unit Test 3";
		allPass = (this.dao.find("kberry@juno.com") == null)& allPass;// : "Delete User Unit Test 4 (make sure kimberly is really gone";
		
		
		if(allPass){
		
			return new ModelAndView("passed");
		}else{
			return new ModelAndView("failed");
		}
	}
}