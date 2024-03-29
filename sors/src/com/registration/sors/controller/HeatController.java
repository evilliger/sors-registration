//-------------------------------------------//
// Name: HeatController		    			 //
// Purpose: Spring Cotroller				 //
//-------------------------------------------//
package com.registration.sors.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

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

import com.registration.sors.handler.MaintainHeatsHandler;
import com.registration.sors.helpers.Security;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Event;
import com.registration.sors.model.Heat;
import com.registration.sors.model.Registration;
import com.registration.sors.model.SystemSession;
import com.registration.sors.service.AthleteDAO;
import com.registration.sors.service.EventDAO;
import com.registration.sors.service.HeatDAO;
import com.registration.sors.service.HeatSpecDAO;
import com.registration.sors.service.RegistrationDAO;

@Controller
@RequestMapping("/heat")
//-------------------------------------------//
//Name: heatController						 //
//Purpose: Implement the functionality of 	 //
//		adding, deleting, updating, and 	 //
//		listing all of the heats. Uses the   //
//		heatdao to accomplish its tasks	     //
//-------------------------------------------//
public class HeatController {
	
	@Autowired private HeatDAO heatDao;
	@Autowired private AthleteDAO athDao;
	@Autowired private RegistrationDAO regDao;
	@Autowired private EventDAO eventDao;
	@Autowired private HeatSpecDAO heatSpecDao;
	
	
	List<String> roles = Arrays.asList("A");
	
	static Logger log = Logger.getLogger(HeatController.class.getName());
	
	// Name: getAddHeatPage
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current user session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		add page - page allowing the user to submit a new heat

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddHeatPage(HttpSession session, ModelMap model) {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
		
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		model.addAttribute("user", ss.getUser());
		return "addHeat";
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
		
		Heat h = new Heat();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heat");
		binder.setRequiredFields(new String[] {"title", "fname", "lname", "pword"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
	
		// Errors will be handled here
		if (!errors.hasErrors()) {
			try{
				if(this.heatDao.add(h) == null) {
					throw new Exception ("Error adding Heat to datastore");
				}else{
					log.warning(ss.getUser().getEmail() + " added a new heat to the datastore");
				}
			}catch(Exception e){
				log.severe("Error adding heat to datastore: " + e.toString());
			}
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Name: getUpdateHeatPage
	// Purpose: Displays the get.jsp page with the information from the User
	// Parameters: session - the current user session
	//			req - heat to update
	//			model - the model for update
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		updateheat page - page to edit heat information

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateHeatPage(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			return "redirect:/user/login";
		}
		model.addAttribute("user", ss.getUser());
		
		Heat h = new Heat();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heat");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here
		if (!errors.hasErrors()) {
			h = this.heatDao.find(h.getId());
			model.addAttribute("heat", h);
		} else {
			log.severe("Error finding heat");
			return "errorPageTemplate";
		}
		return "updateHeat";
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
		
		Heat h = new Heat();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heat");
		binder.setRequiredFields(new String[] {"id", "title", "fname", "lname", "pword"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here		
		if (!errors.hasErrors()) {
			try {
				if((h = this.heatDao.add(h)) == null) 
					return "errorPageTemplate";
				else 
					log.warning(ss.getUser().getEmail() + " updated Heat:" +h.getId() + " in the datastore");
			} catch (Exception e) {
				log.severe("Error updating heat in datastore: " + e.toString());
				return "errorPageTemplate";
			}
		} else {
			log.severe("Error updating heat in datastore");
			return "errorPageTemplate";
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Name: delete
	// Purpose: Deletes a Heat based on information submitted
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
		
		Heat h = new Heat();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heat");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		
		// Errors will be handled here		
		this.heatDao.delete(h);

		// return to list
		return "redirect:list";

	}

	// Name: list
	// Purpose: get a list of all heats
	// Parameters: session - the current user session
	//				model - the model for list
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - list of heats
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listHeats(ModelMap model, HttpSession session) {	
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login";
		}
		model.addAttribute("user", ss.getUser());
		
		// Errors will be handled here
		
		List<Heat> list = this.heatDao.loadAll();
		List<Event>eventList = this.eventDao.loadAll();
		Dictionary<String, String>eList = new Hashtable<String,String>();
		for(int i = 0; i < eventList.size(); ++i){
			Event e = eventList.get(i);
			eList.put(e.getId().toString(), e.getName());
		}
		//list.remove(0);
		model.addAttribute("heatList",list);
		model.addAttribute("eList",eList);
		return "listHeat";
	}
	
	// Name: generate get
		// Purpose: generate heats
		// Parameters: session - the current user session
		//				model - the model for list
		// Return: page redirect
		//		login page - if user not logged in or not authorized
		//		warning page 
	@RequestMapping(value = "/generate", method = RequestMethod.GET)
	public String getGenerateHeatPage(HttpSession session, ModelMap model) {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
		
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		model.addAttribute("user", ss.getUser());
		log.warning(ss.getUser().getEmail() + " requested generate Heats");
		MaintainHeatsHandler handler = new MaintainHeatsHandler(heatDao,regDao,athDao,eventDao,heatSpecDao);
		
		model.addAttribute("errors", handler.validate());
		
		return "generateHeat";
	}


	// Name: generate post
	// Purpose: generate heats
	// Parameters: session - the current user session
	//				model - the model for list
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		generates heats and produces a page with heat listings 
	@RequestMapping(value = "/generate", method = RequestMethod.POST)
	public String generate(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login";
		}
		List<Heat> heatList = this.heatDao.loadAll();
		for(int i = 0; i < heatList.size(); ++i){
			Heat h = heatList.get(i);
			this.heatDao.delete(h);
		}
		String error = "";
		Dictionary<String,Athlete>athDictionary = new Hashtable<String,Athlete>();
		Dictionary<String,Event>eventDictionary= new Hashtable<String,Event>();
		Dictionary<String,List<Registration>>regDictionary= new Hashtable<String,List<Registration>>();
		List<Heat>hList= new ArrayList<Heat>();
		try{
			MaintainHeatsHandler handler = new MaintainHeatsHandler(heatDao,regDao,athDao,eventDao,heatSpecDao);
			handler.GenerateHeats();

			athDictionary = handler.athDictionary;
			eventDictionary = handler.eventDictionary;
			hList = handler.heatList;
			regDictionary = handler.regDictionary;
			
		}catch(Exception e){
			//model.addAttribute("htmlTable", "Heat Generation encountered an error. Please check completeness of records.");
			log.severe("Error was encountered in Generating heats: " + e.toString());
			error = "Heat Generation encountered an error. Please check completeness of records.";
		}
		model.addAttribute("athDictionary",athDictionary);
		model.addAttribute("eventDictionary",eventDictionary);
		model.addAttribute("heatList",hList);
		model.addAttribute("regDictionary",regDictionary);
		model.addAttribute("error",error);
		return "generate";

	}
	
	
}