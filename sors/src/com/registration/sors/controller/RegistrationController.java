//-------------------------------------------//
// Name: RegistrationController	  			 //
// Purpose: Spring Controller				 //
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
import com.registration.sors.model.SystemSession;
import com.registration.sors.model.Registration;
import com.registration.sors.service.RegistrationDAO;

@Controller
@RequestMapping("/registration")
//-------------------------------------------//
//Name: RegistrationController						 //
//Purpose: Implement the functionality of 	 //
//		adding, deleting, updating, and 	 //
//		listing all of the registrations. Uses the   //
//		registrationdao to accomplish its tasks	     //
//-------------------------------------------//
public class RegistrationController {
	
	@Autowired private RegistrationDAO dao;
	
	List<String> roles = Arrays.asList("A");
	

	// Name: getAddRegistrationPage
	// Purpose: Determine if the registration is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current registration session
	// Return: page redirect
	//		login page - if registration not logged in or not authorized
	//		add page - page allowing the registration to submit a new registration

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddRegistrationPage(HttpSession session) {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
		
			session.invalidate();
			
			// Right now it takes the registration back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		return "addRegistration";
	}


	// Dev Testing ONLY
	// Name: init
	// Purpose: Determine if the registration is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current registration session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - list of registrations
	
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(HttpSession session) {
		// Errors will be handled here
		this.dao.init();
		return new ModelAndView("redirect:list");
	}

	// Name: add
	// Purpose: Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	// Parameters: session - the current registration session
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
		
		Registration r = new Registration();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(r, "registration");
		binder.setRequiredFields(new String[] {"athleteID", "eventID", "score"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
	
		// Errors will be handled here
		if (!errors.hasErrors()) {
			
			if(this.dao.add(r) == null) {
				throw new Exception ("Error adding Registration to datastore");
			}
			
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Name: getUpdateRegistrationPage
	// Purpose: Displays the get.jsp page with the information from the Registration
	// Parameters: session - the current registration session
	//			req - registration to update
	//			model - the model for update
	// Return: page redirect
	//		login page - if registration not logged in or not authorized
	//		updateregistration page - page to edit registration information

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateRegistrationPage(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			return "redirect:/user/login";
		}
		
		Registration r = new Registration();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(r, "registration");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here
		if (!errors.hasErrors()) {
			Long i = r.getId();
			r = this.dao.find(r.getId());
			model.addAttribute("registration", r);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "updateRegistration";
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
		
		Registration r = new Registration();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(r, "registration");
		binder.setRequiredFields(new String[] {"id", "athleteID", "eventID", "score"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here		
		if (!errors.hasErrors()) {
			this.dao.update(r);
		} else {
			throw new Exception ("Supply ID, Name, and Email");
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Name: delete
	// Purpose: Deletes a Registration based on information submitted
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
		
		Registration r = new Registration();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(r, "registration");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		
		// Errors will be handled here		
		this.dao.delete(r);

		// return to list
		return "redirect:list";

	}

	// Name: list
	// Purpose: get a list of all registrations
	// Parameters: session - the current user session
	//				model - the model for list
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - list of registrations
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listRegistrations(ModelMap model, HttpSession session) {	
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login";
		}
		
		// Errors will be handled here
		
		List<Registration> list = this.dao.loadAll();
		list.remove(0);
		model.addAttribute("registrationList",list);
		return "listRegistration";
	}
}