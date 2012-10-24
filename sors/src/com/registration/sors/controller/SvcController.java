package com.registration.sors.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.registration.sors.model.Contact;
import com.registration.sors.service.ContactDAO;

@Controller
@RequestMapping("/svc")
public class SvcController {

	
	@Autowired
	private ContactDAO ContactDAO;
	
	// Returns a JSON of all the contacts in the datastore
	@RequestMapping(value = "/list", method = RequestMethod.GET, headers="Accept=application/json")	
	public @ResponseBody List<Contact> getList() {		
		return ContactDAO.LoadAll();
	}
	
	// Accepts a name as a query parameter and returns a JSON containing matches to the name
	@RequestMapping(value = "/search", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody List<Contact> searchContactName(HttpServletRequest req) {
		
		String name = (String) req.getParameter("name");		
		List<Contact> l = ContactDAO.NameSearch(name);		
		return l;
	}
	
	// Adds a contact to the datastore based on the query parameters
	@RequestMapping(value = "/add", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody Contact addContact(HttpServletRequest req) throws Exception {
		
		Contact c = new Contact();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(c, "contact");
		binder.setRequiredFields(new String[] {"name", "email"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			if(ContactDAO.addContact(c) == true) {
				return c;
			} else {
				throw new Exception ("Error adding contact to datastore");
			}
		} else {
			throw new Exception ("Supply Name and Email");
		}
	}
	
	// Updates an existing entry in the datastore based on query parameters
	@RequestMapping(value = "/update", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody Contact updateContact(HttpServletRequest req) throws Exception {

		Contact c = new Contact();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(c, "contact");
		binder.setRequiredFields(new String[] {"id", "name", "email"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			// TODO: useful if block is useful.
			if(ContactDAO.UpdateContact(c) == true) {
				return ContactDAO.GetContactByID(c.getId());
			} else {
				throw new Exception ("Error updating datastore");
			}
		} else {
			throw new Exception ("Supply ID, Name, and Email");
		}
	}
	
	// Deletes a contact from the datastore based on the query parameters
	@RequestMapping(value = "/delete", method = RequestMethod.GET, headers="Accept=application/json")
	public @ResponseBody String deleteContact(HttpServletRequest req) throws Exception {
		
		Contact c = new Contact();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(c, "contact");
		binder.setRequiredFields(new String[] {"id", "name", "email"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			// TODO: useful if block is useful.
			if(ContactDAO.DeleteContact(c) == true) {
				return "She Gone";
			} else {
				// Something bad happened.
				throw new Exception ("Ensure the validity of the submitted ID");
			}
		} else {
			throw new Exception ("Supply ID, Name, and Email");
		}
	}
	
	
}
