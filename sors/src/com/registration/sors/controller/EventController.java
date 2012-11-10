//-------------------------------------------//
// Name: EventController        			 //
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
import com.registration.sors.model.Event;
import com.registration.sors.service.EventDAO;


@Controller
@RequestMapping("/event")
//-------------------------------------------//
//Name: eventController						 //
//Purpose: Implement the functionality of 	 //
//		adding, deleting, updating, and 	 //
//		listing all of the events. Uses the   //
//		eventdao to accomplish its tasks	     //
//-------------------------------------------//
public class EventController {
	
	@Autowired private EventDAO dao;
	
	List<String> roles = Arrays.asList("A");
	
	
	// Name: getAddEventPage
	// Purpose: Determine if the event is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current event session
	// Return: page redirect
	//		login page - if event not logged in or not authorized
	//		add page - page allowing the event to submit a new event

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddEventPage(HttpSession session) {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
		
			session.invalidate();
			
			// Right now it takes the event back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		return "addEvent";
	}

	// Name: add
	// Purpose: Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	// Parameters: session - the current event session
	//				req - the data to add
	// Return: page redirect
	//		login page - if event not logged in or not authorized
	//		list page - when data is added
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest req, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the event back to the Login Page no matter what
			return new ModelAndView("redirect:/user/login");
		}
		
		Event e = new Event();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(e, "event");
		binder.setRequiredFields(new String[] {"name", "units"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
	
		// Errors will be handled here
		if (!errors.hasErrors()) {
			
			if(this.dao.add(e) == null) {
				throw new Exception ("Error adding Event to datastore");
			}
			
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Name: getUpdateEventPage
	// Purpose: Displays the get.jsp page with the information from the Event
	// Parameters: session - the current event session
	//			req - event to update
	//			model - the model for update
	// Return: page redirect
	//		login page - if event not logged in or not authorized
	//		updateevent page - page to edit event information

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateEventPage(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			return "redirect:/user/login";
		}
		
		Event e = new Event();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(e, "event");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here
		if (!errors.hasErrors()) {
			Long i = e.getId();
			e = this.dao.find(e.getId());
			model.addAttribute("event", e);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "updateEvent";
	}
	// Name: update
	// Purpose: Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	// Parameters: session - the current event session
	//				req - the entry to update
	// Return: page redirect
	//		login page - if event not logged in or not authorized
	//		list page - when data is updated
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the event back to the Login Page no matter what
			return "redirect:/user/login";
		}
		
		Event e = new Event();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(e, "event");
		binder.setRequiredFields(new String[] {"id", "name", "units"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here		
		if (!errors.hasErrors()) {
			this.dao.update(e);
		} else {
			throw new Exception ("Supply ID, Name, and Units");
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Name: delete
	// Purpose: Deletes a Event based on information submitted
	// Parameters: session - the current event session
	//				req - the entry to delete
	// Return: page redirect
	//		login page - if event not logged in or not authorized
	//		list page - when data is deleted
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the event back to the Login Page no matter what
			return "redirect:/user/login";
		}
		
		Event u = new Event();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "event");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		
		// Errors will be handled here		
		this.dao.delete(u);

		// return to list
		return "redirect:list";

	}

	// Name: list
	// Purpose: get a list of all events
	// Parameters: session - the current event session
	//				model - the model for list
	// Return: page redirect
	//		login page - if event not logged in or not authorized
	//		list page - list of events
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listEvents(ModelMap model, HttpSession session) {	
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the event back to the Login Page no matter what
			return "redirect:/user/login";
		}
		
		// Errors will be handled here
		
		List<Event> list = this.dao.loadAll();
		model.addAttribute("eventList",list);
		return "listEvent";
	}
}