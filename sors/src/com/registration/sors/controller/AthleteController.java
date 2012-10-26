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

import com.registration.sors.helpers.Authenticator;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.SystemSession;
import com.registration.sors.service.AthleteDAO;

@SuppressWarnings({"javadoc"})
@Controller
@RequestMapping("/athlete")
public class AthleteController {
	
	@Autowired private AthleteDAO dao;
	
	List<String> roles = Arrays.asList("A", "T");

	// Dev Testing ONLY
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(HttpSession session) {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return new ModelAndView("redirect:/user/login");
		}
		this.dao.init();
		return new ModelAndView("redirect:list");
	}
	
	// Returns the add.jsp page allowing the user to submit a new contact
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddContactPage(HttpSession session) {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		return "addAthlete";
	}

	// Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
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
				throw new Exception ("Error adding contact to datastore");
			}
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Displays the get.jsp page with the information from the contact
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateContactPage(HttpServletRequest req, ModelMap model, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handle here
		if (!errors.hasErrors()) {
				model.addAttribute("athlete", a);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "updateAthlete";
	}
	
	// Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
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
	
	// Deletes a contact based on information submitted
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
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

	// get all contacts
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listContact(ModelMap model, HttpSession session) {	
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		// Errors will be handled here
		model.addAttribute("athleteList", this.dao.loadAll());
		return "listAthlete";
	}
}