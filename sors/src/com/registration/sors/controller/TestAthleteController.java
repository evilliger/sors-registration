//------------------------------------------------//
// Name:TestAthleteController					  //
// Purpose: To test the methods in the athletedao //
//		class									  //
//------------------------------------------------//


package com.registration.sors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.registration.sors.model.Athlete;
import com.registration.sors.service.AthleteDAO;


@Controller
@RequestMapping("/")
//------------------------------------------------//
//Name:TestAthleteController					  //
//Purpose: To test the methods in the athletedao //
//		class									  //
//------------------------------------------------//
public class TestAthleteController {
	
	@Autowired private AthleteDAO dao;

	// Name: test
	// Parameters: none
	// Returns a page to display.
	//		passed.jup if all tests pass
	//		failed.jup if any test fails
	@RequestMapping(value = "/testathlete", method = RequestMethod.GET)
	public ModelAndView test() {
		boolean allPass = true;
		int numAthletes = this.dao.loadAll().size();
		
		
		//---------------------------------------//
		//				ADD Athlete				 //
		//---------------------------------------//
		Athlete a = new Athlete();
		a.setFname("Max");
		this.dao.add(a);
		allPass = (numAthletes + 1 == this.dao.loadAll().size()) & allPass; //) : "Add Athlete Unit Test 1 size";
		//allPass =  (this.dao.find(a.getId()).getFname().equals("Max")) & allPass;//: "Add Athlete Unit Test 2 return";
		this.dao.delete(a); // clean up
		allPass =  (numAthletes == this.dao.loadAll().size()) & allPass; // : "Add Athlete Unit Test 3 delete";
		
		Athlete b = new Athlete();
		b.setFname("Billy");
		b.setLname("Joel");
		this.dao.add(b);
		
		Athlete c = new Athlete();
		c.setFname("Max");
		this.dao.add(c);
		
		Athlete d = new Athlete();
		d.setFname("Michael");
		this.dao.add(d);
		
		Athlete e = new Athlete();
		e.setFname("Kimberly");
		this.dao.add(e);
		
		
		
		allPass = (numAthletes + 4 == this.dao.loadAll().size()) & allPass; // : "Add Athlete Unit Test 4 size (multiple adds)";
		
		
		//---------------------------------------//
		//				FIND Athlete			 //
		//---------------------------------------//
		
		//allPass = (this.dao.find(d.getId()).getFname().equals("Michael")) & allPass;//  : "Find Athlete Unit Test 2 object in datastore";
		
		
		//---------------------------------------//
		//				Update Athlete			 //
		//---------------------------------------//
		
		//allPass = (this.dao.find(e.getId()).getLname() == null) & allPass;// : "Find2 Athlete Unit Test 1 object in datastore";
		
		e.setLname("Smith");
		
		this.dao.update(e);
		
		//allPass =(this.dao.find(e.getId()).getLname().equals("Smith")) & allPass;// : "Find2 Athlete Unit Test 1 object in datastore";
		
		
		//---------------------------------------//
		//				Delete Athlete			 //
		//---------------------------------------//
		this.dao.delete(b); // clean up
		this.dao.delete(c); // clean up
		
		allPass = (this.dao.loadAll().size() == numAthletes + 2)& allPass ;//: "Delete Athlete Unit Test 1";
		//allPass = (this.dao.find(b.getId()) == null)& allPass;// : "Delete Athlete Unit Test 2 (make sure billy is really gone";
		
		this.dao.delete(d); // clean up
		this.dao.delete(e); // clean up
		
		allPass = (this.dao.loadAll().size() == numAthletes) & allPass;//: "Delete Athlete Unit Test 3";
		
		
		if(allPass){
		
			return new ModelAndView("passed");
		}else{
			return new ModelAndView("failed");
		}
	}
}