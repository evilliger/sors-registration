//-------------------------------------------//
// Name: EventConflictController	  		 //
// Purpose: Spring Controller				 //
//-------------------------------------------//
package com.registration.sors.controller;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
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
import com.registration.sors.model.Event;
import com.registration.sors.model.EventConflict;
import com.registration.sors.model.SystemSession;
import com.registration.sors.service.EventConflictDAO;
import com.registration.sors.service.EventDAO;

@Controller
@RequestMapping("/eventconflict")
//---------------------------------------------------//
//Name: EventConflictController						 //
//Purpose: Implement the functionality of 	 		 //
//		adding, deleting, updating, and 			 //
//		listing all of the event conflicts. Uses the //
//		eventconflictdao to accomplish its task      //
//---------------------------------------------------//
public class EventConflictController {
	
	@Autowired private EventConflictDAO dao;
	@Autowired private EventDAO Evdao;
	
	List<String> roles = Arrays.asList("A");
	

	// Name: getAddEventConflictPage
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddEventConflictPage(ModelMap model, HttpSession session) {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login"; 
		}
		
		List<Event> events = Evdao.loadAll();
		model.addAttribute("events", events);
		
		return "addEventConflict";
	}

	// Name: add
	// Purpose: Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(ModelMap model, HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return new ModelAndView("redirect:/user/login");
		}
		
		EventConflict ec = new EventConflict();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(ec, "eventconflict");
		binder.setRequiredFields(new String[] {"e1Id", "e2Id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
	
		// Errors will be handled here
		if (!errors.hasErrors()) {
			if(this.dao.add(ec) == null) {
				throw new Exception ("Error adding Event Conflict to datastore");
			}
			
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Name: getUpdateEventConflictPage
	// Purpose: Displays the get.jsp page with the information from the EventConflict
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateEventConflictPage(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login";
		}
		
		EventConflict ec = new EventConflict();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(ec, "eventconflict");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		List<Event> events = Evdao.loadAll();
		model.addAttribute("events", events);
		
		// Errors will be handled here
		if (!errors.hasErrors()) {
			ec = this.dao.find(ec.getId());
			model.addAttribute("eventconflict", ec);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "updateEventConflict";
	}
	// Name: update
	// Purpose: Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login";
		}

		EventConflict ec = new EventConflict();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(ec, "eventconflict");
		binder.setRequiredFields(new String[] {"e1Id", "e2Id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here		
		if (!errors.hasErrors()) {
			this.dao.update(ec);
		} else {
			throw new Exception ("Please supply Event 1 and Event 2");
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Name: delete
	// Purpose: Deletes an EventConflict based on information submitted
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login";
		}
		
		EventConflict ec = new EventConflict();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(ec, "eventconflict");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		
		// Errors will be handled here		
		this.dao.delete(ec);

		// return to list
		return "redirect:list";

	}

	// Name: list
	// Purpose: get a list of all event conflicts
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model, HttpSession session) {	
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login";
		}
		
		List<EventConflict> list = this.dao.loadAll();
		model.addAttribute("eventConflictList",list);
		
		Dictionary<Long, String> eventNames = new Hashtable<Long, String>();
		List<Event> events = Evdao.loadAll();
		for (Event e : events) {
			eventNames.put(e.getId(), e.getName());
		}
		model.addAttribute("eventNames", eventNames);
		
		return "listEventConflict";
	}
}