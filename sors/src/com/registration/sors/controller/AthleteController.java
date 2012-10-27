//-------------------------------------------//
// Name: AthleteController					 //
// Purpose: Spring Cotroller				 //
//-------------------------------------------//
package com.registration.sors.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.registration.sors.helpers.Security;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.SystemSession;
import com.registration.sors.service.AthleteDAO;

@Controller
@RequestMapping("/athlete")
//-------------------------------------------//
//Name: AthleteController					 //
//Purpose: Implement the functionality of 	 //
//		adding, deleting, updating, and 	 //
//		listing all of the athletes. Uses the//
//		athletedao to accomplish its tasks	 //
//-------------------------------------------//
public class AthleteController {
	
	@Autowired private AthleteDAO dao;
	
	List<String> roles = Arrays.asList("A", "T");

	// Dev Testing ONLY
	// Name: init
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current user session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - if user is logged in and authorized
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(HttpSession session) {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return new ModelAndView("redirect:/user/login");
		}
		this.dao.init();
		return new ModelAndView("redirect:list");
	}
	
	// Name: getAddAthletePage
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current user session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		add page - page allowing the user to submit a new athlete
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddAthletePage(HttpSession session) {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		return "addAthlete";
	}

	
	// Name: add
	// Purpose: Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	// Parameters: session - the current user session
	//				req - the data to add
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - when data is added

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest req, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return new ModelAndView("redirect:/user/login");
		}
		
		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"fname", "lname", "mname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		//Errors will be handled here
		if (!errors.hasErrors()) {
			
			if(this.dao.add(a) == null) {
				throw new Exception ("Error adding Athlete to datastore");
			}
			
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Name: getUpdateAthletePage
	// Purpose: Displays the get.jsp page with the information from the Athlete
	// Parameters: session - the current user session
	//			req - athlete to update
	//			model - the model for update
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		updateathlete page - page to edit athlete information
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateAthletePage(HttpServletRequest req, ModelMap model, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		a = dao.find(a.getId());
		
		// Errors will be handle here
		if (!errors.hasErrors()) {
				model.addAttribute("athlete", a);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "updateAthlete";
	}
	
	
	// Name: update
	// Purpose: Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	// Parameters: session - the current user session
	//				req - the entry to update
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - when data is updated
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id", "fname", "lname", "mname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here
		if (!errors.hasErrors()) {
			this.dao.update(a);
		} else {
			throw new Exception ("Supply required information.");
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Name: delete
	// Purpose: Deletes a Athlete based on information submitted
	// Parameters: session - the current user session
	//				req - the entry to delete
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - when data is deleted
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

		Athlete a = new Athlete();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		//BindingResult errors = binder.getBindingResult();
		// Errors will be handled here
		this.dao.delete(a);

		// return to list
		return "redirect:list";

	}
	
	// Name: list
	// Purpose: get a list of all Athletes
	// Parameters: session - the current user session
	//				model - the model for list
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - list of athletes
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model, HttpSession session) {	
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		
		// Errors will be handled here
		model.addAttribute("athleteList", this.dao.loadAll());
		return "listAthlete";
	}
}