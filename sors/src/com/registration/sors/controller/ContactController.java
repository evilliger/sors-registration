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

import com.registration.sors.model.Contact;
import com.registration.sors.service.ContactDAO;

@Controller
@RequestMapping("/contact")
public class ContactController {
	
	@Autowired private ContactDAO ContactDAO;
	
	// Returns the add.jsp page allowing the user to submit a new contact
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddContactPage(ModelMap model) {
		return "add";
	}
	
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(ModelMap model) {
		ContactDAO.init();
		return new ModelAndView("redirect:list");
	}

	// Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest req, ModelMap model) throws Exception {

		Contact c = new Contact();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(c, "contact");
		binder.setRequiredFields(new String[] {"name", "email"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			if(ContactDAO.addContact(c) != true) {
				throw new Exception ("Error adding contact to datastore");
			}
		} else {
			throw new Exception ("Supply Name and Email");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Displays the get.jsp page with the information from the contact
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateContactPage(HttpServletRequest req, ModelMap model) throws Exception {
		
		Contact c = new Contact();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(c, "contact");
		binder.setRequiredFields(new String[] {"id", "name", "email"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
				model.addAttribute("contact", c);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "update";
	}
	
	// Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, ModelMap model) throws Exception {
		
		Contact c = new Contact();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(c, "contact");
		binder.setRequiredFields(new String[] {"id", "name", "email"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			if(ContactDAO.UpdateContact(c) != true) {
				throw new Exception ("Error updating datastore");
			}
		} else {
			throw new Exception ("Supply ID, Name, and Email");
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Deletes a contact based on information submitted
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, ModelMap model) throws Exception {

		Contact c = new Contact();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(c, "contact");
		binder.setRequiredFields(new String[] {"id", "name", "email"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			// TODO: useful if block is useful.
			if(ContactDAO.DeleteContact(c) != true) {
				// Something bad happened.
				throw new Exception ("Ensure the validity of the submitted ID");
			}
		} else {
			throw new Exception ("Supply ID, Name, and Email");
		}

		// return to list
		return "redirect:/list";

	}

	// get all contacts
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listContact(ModelMap model) {	
		model.addAttribute("contactList", ContactDAO.LoadAll());
		return "list";
	}
}