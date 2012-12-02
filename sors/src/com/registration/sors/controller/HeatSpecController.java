//-------------------------------------------//
// Name: HeatSpecController					 //
// Purpose: Spring Cotroller				 //
//-------------------------------------------//
package com.registration.sors.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.registration.sors.helpers.Security;
import com.registration.sors.model.Event;
import com.registration.sors.model.HeatSpec;
import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;
import com.registration.sors.service.EventDAO;
import com.registration.sors.service.HeatSpecDAO;

@Controller
@RequestMapping("/heatspec")
//-----------------------------------------------//
//Name: HeatSpecController						 //
//Purpose: Implement the functionality of 		 //
//		adding, deleting, updating, and 	 	 //
//		listing all of the HeatSpecs. Uses the	 //
//		heatspecdao to accomplish its tasks		 //
//-----------------------------------------------//
public class HeatSpecController {
	
	@Autowired private HeatSpecDAO dao;
	@Autowired private EventDAO Evdao;
	
	List<String> roles = Arrays.asList("A", "T");
	
	// Name: getAddHeatSpecPage
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current user session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		add page - page allowing the user to submit a new heatspec
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddHeatSpecPage(HttpServletRequest req, HttpSession session, ModelMap model) {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login"; 
		}
		model = loadModel(model, req, true, ss.getUser(), new HeatSpec(), null);
		
		return "maintainHeatSpec";
	}

	
	// Name: add
	// Purpose: Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	// Parameters: session - the current user session
	//				req - the data to add
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - when data is added

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(HttpServletRequest req, ModelMap model, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login"; 
		}

		HeatSpec h = new HeatSpec();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heatspec");
		binder.setRequiredFields(new String[] {"eventId", "minAge", "maxAge", "gender", "maxInHeat", "numHeats"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			if((h = this.dao.add(h)) == null) 
				return "errorPageTemplate";
		} else {
			model = loadModel(model,req,false,ss.getUser(),h,errors);
			return "maintainHeatSpec";
		}
		
		// return to list
		return "redirect:list";
	}
	
	// Name: getUpdateHeatSpecPage
	// Purpose: Displays the get.jsp page with the information from the HeatSpec
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateHeatSpecPage(HttpServletRequest req, ModelMap model, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login"; 
		}

		HeatSpec h = new HeatSpec();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heatspec");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();

		h = dao.find(h.getId());

		if (errors.hasErrors()) 
			return "redirect:list";
		
		model = loadModel(model,req,false,ss.getUser(),h,errors);

		return "maintainHeatSpec";
	}
	
	
	// Name: update
	// Purpose: Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	// Parameters: session - the current user session
	//				req - the entry to update
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - when data is updated
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, ModelMap model, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login"; 
		}

		HeatSpec h = new HeatSpec();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heatspec");
		binder.setRequiredFields(new String[] {"id", "eventId", "gender", "minAge", "maxAge", "numHeats", "maxInHeat"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();

		if (!errors.hasErrors()) {
			if((h = this.dao.add(h)) == null) 
				return "errorPageTemplate";
		} else {
			model = loadModel(model,req,false,ss.getUser(),h,errors);
			return "maintainHeatSpec";
		}
		// return to list
		return "redirect:list";

	}
	
	// Name: delete
	// Purpose: Deletes a HeatSpec based on information submitted
	// Parameters: session - the current user session
	//				req - the entry to delete
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - when data is deleted
	
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		model.addAttribute("user", ss.getUser());

		HeatSpec h = new HeatSpec();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heatspec");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		//BindingResult errors = binder.getBindingResult();
		// Errors will be handled here
		this.dao.delete(h);

		// return to list
		return "redirect:list";

	}
	
	// Name: list
	// Purpose: get a list of all HeatSpecs
	// Parameters: session - the current user session
	//				model - the model for list
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - list of HeatSpecs
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model, HttpSession session) {	
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		model.addAttribute("user", ss.getUser());
		
		// Errors will be handled here
		List<Event> eventList = this.Evdao.loadAll();
		Dictionary<String,String>eList = new Hashtable<String,String>();
		for(int i = 0; i < eventList.size(); ++i){
			Event e = eventList.get(i);
			eList.put(e.getId().toString(), e.getName());
		}
		model.addAttribute("heatSpecList", this.dao.loadAll());
		model.addAttribute("eventList", eList);
		return "listHeatSpec";
	}
	
	// ********************** HELPER METHODS **************************
	// Loads the model for the maintain heatspecs jsp
	private ModelMap loadModel(ModelMap model, HttpServletRequest req, boolean add, User u, HeatSpec h, BindingResult errors) {
		try{
			List<Event> events = this.Evdao.loadAll();

			model.addAttribute("user", u);
			model.addAttribute("add", add);
			model.addAttribute("events", events);
			
			try{
				if(req.getParameter("evid")!=null)
					model.addAttribute("evid", Long.parseLong(req.getParameter("evid")));
				else model.addAttribute("evid", -1L);
			} catch (Exception e){
				model.addAttribute("evid", -1L);
			}
			
			if(errors != null)
				model.addAllAttributes(errors.getModel()); 
		} catch (Exception e){
			return null;
		}
		model.addAttribute("heatspec", h);
		return model;
	}
	
	// Special binder to customize the output of the date
	@InitBinder
	public void binder(WebDataBinder binder) {binder.registerCustomEditor(Date.class,
	    new PropertyEditorSupport() {
	        public String getAsText() {
	        	Object val;
				if((val = getValue()) != null)
					return new SimpleDateFormat("hh:mm").format(val);
				else 
					return "";
        	}
	    });
	}
}