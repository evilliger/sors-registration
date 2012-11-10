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
import com.registration.sors.model.HeatSpec;
import com.registration.sors.model.SystemSession;
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
	
	List<String> roles = Arrays.asList("A", "T");
	
	// Name: getAddAthletePage
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current user session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		add page - page allowing the user to submit a new athlete
	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddAthletePage(HttpSession session, ModelMap model) {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();	
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		
		HeatSpec h = new HeatSpec();
		
		model.addAttribute("heatspec", h);
		model.addAttribute("add", true);
		
		return "addHeatSpec";
	}

	
	// Name: add
	// Purpose: Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	// Parameters: session - the current user session
	//				req - the data to add
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - when data is added

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest req, ModelMap model, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return new ModelAndView("redirect:/user/login");
		}
		
		HeatSpec h = new HeatSpec();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heatspec");
		binder.setRequiredFields(new String[] {"eventId", "gender", "minAge", "maxAge", "time", "numHeats", "maxInHeat"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();

		// Errors will be handled here
		if (!errors.hasErrors()) {
			
			if(this.dao.add(h) == null) {
				throw new Exception ("Error adding HeatSpec to datastore");
			}
			
		} else {
			throw new Exception ("Supply required information." + errors.getAllErrors().get(0).toString());
		}
		return new ModelAndView("redirect:list");
	}
	
	// Name: getUpdateHeatSpecPage
	// Purpose: Displays the get.jsp page with the information from the Athlete
	// Parameters: session - the current user session
	//			req - athlete to update
	//			model - the model for update
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		updateathlete page - page to edit athlete information
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateHeatSpecPage(HttpServletRequest req, ModelMap model, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

		HeatSpec h = new HeatSpec();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heatspec");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		h = dao.find(h.getId());
		
		// Errors will be handle here
		if (!errors.hasErrors()) {
			model.addAttribute("heatspec", h);
		} else {
			model.addAttribute("heatspec", h);
			model.addAllAttributes(errors.getModel()); 
			return "updateHeatSpec";
		}
		return "updateHeatSpec";
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
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

		HeatSpec h = new HeatSpec();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(h, "heatspec");
		binder.setRequiredFields(new String[] {"eventId", "gender", "minAge", "maxAge", "time", "numHeats", "maxInHeat"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here
		if (!errors.hasErrors()) {
			this.dao.update(h);
		} else {
			model.addAttribute("heatspec", h);
			model.addAllAttributes(errors.getModel()); 
			return "updateHeatSpec";
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
	public String delete(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

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
		
		// Errors will be handled here
		model.addAttribute("heatSpecList", this.dao.loadAll());
		return "listHeatSpec";
	}
}