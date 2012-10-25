package com.registration.sors.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.registration.sors.model.Athlete;
import com.registration.sors.service.AthleteDAO;

@SuppressWarnings("javadoc")
@Controller
@RequestMapping("/athlete")
public class AthleteController {
	
	@Autowired private AthleteDAO dao;
	
	// Returns the add.jsp page allowing the user to submit a new contact
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddContactPage(ModelMap model) {
		return "addAthlete";
	}

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(ModelMap model) {
		dao.init();
		return new ModelAndView("redirect:list");
	}

	// Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest req, ModelMap model) throws Exception {

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"fname", "lname", "mname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		if (!errors.hasErrors()) {
			if(dao.add(a) == null) {
				throw new Exception ("Error adding contact to datastore");
			}
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Displays the get.jsp page with the information from the contact
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateContactPage(HttpServletRequest req, ModelMap model) throws Exception {

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"fname", "lname", "mname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
				model.addAttribute("athlete", a);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "updateAthlete";
	}
	
	// Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, ModelMap model) throws Exception {

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id", "fname", "lname", "mname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			dao.update(a);
		} else {
			throw new Exception ("Supply required information.");
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Deletes a contact based on information submitted
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, ModelMap model) throws Exception {

		Athlete a = new Athlete();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
	
		dao.delete(a);

		// return to list
		return "redirect:list";

	}

	// get all contacts
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listContact(ModelMap model) {	
		model.addAttribute("athleteList", dao.loadAll());
		return "listAthlete";
	}
}