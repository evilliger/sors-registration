//-------------------------------------------//
// Name: AthleteController					 //
// Purpose: Spring Cotroller				 //
//-------------------------------------------//
package com.registration.sors.controller;

import java.util.ArrayList;
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

import com.registration.sors.helpers.Security;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.Event;
import com.registration.sors.model.Registration;
import com.registration.sors.model.School;
import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;
import com.registration.sors.service.AthleteDAO;
import com.registration.sors.service.ClassroomDAO;
import com.registration.sors.service.EventDAO;
import com.registration.sors.service.RegistrationDAO;
import com.registration.sors.service.SchoolDAO;

@Controller
@RequestMapping("/athlete")
//-------------------------------------------//
//Name: AthleteController					 //
//Purpose: Implement the functionality of 	 //
//		adding, deleting, updating, and 	 //
//		listing all of the athletes. Uses the//
//		athletedao to accomplish its tasks	 //
//-------------------------------------------//
public class AthleteController {
	
	@Autowired private AthleteDAO Athdao;
	@Autowired private EventDAO Evdao;
	@Autowired private SchoolDAO Schdao;
	@Autowired private ClassroomDAO Cladao;
	@Autowired private RegistrationDAO Regdao;
	
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
			return "redirect:/user/login"; 
		}
		
		model = loadModel(model, true, ss.getUser(), new Athlete(), null,null);
		
		return "maintainAthlete";
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
		
		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"fname", "lname", "mname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		List<String> otherErrors = handleEvents(req, a, !errors.hasErrors());
		
		//Errors will be handled here
		if (!errors.hasErrors() && otherErrors.isEmpty()) {
			if(this.Athdao.add(a) == null) 
				return "errorPageTemplate";
			
		} else {
			model = loadModel(model,true,ss.getUser(),a,errors,otherErrors);
			return "maintainAthlete";
		}
		return "redirect:list";
	}
	
	// Name: getUpdateAthletePage
	// Purpose: Displays the get.jsp page with the information from the Athlete
	// Parameters: session - the current user session
	//			req - athlete to update
	//			model - the model for update
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		updateathlete page - page to edit athlete information
	
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateAthletePage(HttpServletRequest req, ModelMap model, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login"; 
		}

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		Classroom c = this.Cladao.find(ss.getUser());
		a = Athdao.find(a.getId(), c);

		if (errors.hasErrors()) 
			return "redirect:list";
		
		model = loadModel(model,false,ss.getUser(),a,errors,null);

		return "maintainAthlete";
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

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id", "fname", "lname", "mname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		List<String> otherErrors = handleEvents(req, a, !errors.hasErrors());
		
		// Errors will be handled here
		if (!errors.hasErrors() && otherErrors.isEmpty()) {
			this.Athdao.update(a);
		} else {
			model = loadModel(model,false,ss.getUser(),a,errors,otherErrors);
			return "maintainAthlete";
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Name: delete
	// Purpose: Deletes a Athlete based on information submitted
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
			return "redirect:/user/login"; 
		}

		Athlete a = new Athlete();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
	
		if(errors.hasErrors() || this.Athdao.delete(a) == null)
			return "errorPageTemplate";
		
		return "redirect:list";
	}
	
	// Name: list
	// Purpose: get a list of all Athletes
	// Parameters: session - the current user session
	//				model - the model for list
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - list of athletes
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(ModelMap model, HttpSession session) {	
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login"; 
		}
		
		model.addAttribute("athleteList", this.Athdao.loadAll(ss.getUser()));
		return "listAthlete";
	}
	
	// ********* HELPER METHODS *******************
	
	// Returns a list of errors for when the entered scores for the events are either
	// - less than minScore or greater than maxScore
	public List<String> handleEvents(HttpServletRequest req, Athlete a, boolean storeEvents) {
		List<String> errors = new ArrayList<String>();
		String s = (String)req.getAttribute("pevent") ;
		if (req.getAttribute("pevent") != null && !((String)req.getAttribute("pevent")).equals("-1")) {
			String pevent = (String)req.getAttribute("pevent");
			Event pe = this.Evdao.find(Long.parseLong(pevent));
			if(pe != null){
				Double pscore = 0.0;
				try{
					pscore = Double.parseDouble((String)req.getAttribute("pscore"));
					if (pscore > pe.getMax() || pscore < pe.getMin()) {
						errors.add("Primary event score not within bounds");
					} else {
						Registration r = new Registration(pscore, a.getId(), pe.getId());
						Long regid = -1L;
						if (req.getAttribute("pregId") != null && !((String)req.getAttribute("pregId")).equals("-1")) {
							try {
								regid = (Long)req.getAttribute("sregId");
							} catch (Exception e){
								errors.add("Registration ID in incorrect format.");
							}
						}
						if(regid != -1) {
							Registration tmpReg = new Registration();
							tmpReg.setId(regid);
							this.Regdao.delete(r);
							r.setId(regid);
						}
						if(storeEvents)
							this.Regdao.add(r);
					}
				} catch (Exception e){
					errors.add("Please enter numeric score for primary event.");
				}
			} else {
				errors.add("Primary event not found.");
			}
		}
		
		if (req.getAttribute("ssevent") != null && !((String)req.getAttribute("ssevent")).equals("-1")) {
			String ssevent = (String)req.getAttribute("ssevent");
			Event sse = this.Evdao.find(Long.parseLong(ssevent));
			if(sse != null){
				Double sscore = 0.0;
				try {
					sscore = Double.parseDouble((String)req.getAttribute("sscore"));
					if (sscore > sse.getMax() || sscore < sse.getMin()) {
						errors.add("Secondary event score not within bounds");
					} else {
						Registration r = new Registration(sscore, a.getId(), sse.getId());
						Long regid = -1L;
						if (req.getAttribute("sregId") != null && !((String)req.getAttribute("sregId")).equals("-1")) {
							try {
								regid = (Long)req.getAttribute("pregId");
							} catch (Exception e){
								errors.add("Registration ID in incorrect format.");
							}
						}
						if(regid != -1) {
							Registration tmpReg = new Registration();
							tmpReg.setId(regid);
							this.Regdao.delete(r);
							r.setId(regid);
						}
						if(storeEvents)
							this.Regdao.add(r);
					}
				} catch(Exception e){
					errors.add("Please enter numeric score for secondary event.");
				}
			} else {
				errors.add("Secondary event not found.");
			}
		}
		return errors;
	}
	
	private ModelMap loadModel(ModelMap model, boolean add, User u, Athlete a, BindingResult errors, List<String> otherErrors) {
		
		try{
			List<Event> events = this.Evdao.loadAll();
			List<School> schools = this.Schdao.loadAll();
			List<Classroom> classrooms = this.Cladao.loadAll();
			
			model.addAttribute("user", u);
			model.addAttribute("add", add);
			model.addAttribute("events", events);
			
			if(u.getRole().equals("A")) {
				model.addAttribute("schools",schools);
				model.addAttribute("classrooms",classrooms);
			} else { 
				Classroom c = this.Cladao.find(u);
				model.addAttribute("school", c.getSchoolID());
				a.setClassroomId(c.getId());
			}
			
			List<Registration> regs = Regdao.find(a);
			if(regs.size() > 0)
				model.addAttribute("pregId", regs.get(0).getId());
			else 
				model.addAttribute("pregId", -1L);
			if(regs.size() > 1)
				model.addAttribute("sregId", regs.get(1).getId());
			else
				model.addAttribute("sregId", -1L);
			
			if(errors != null)
				model.addAllAttributes(errors.getModel()); 
			if(otherErrors != null)
				model.addAttribute("otherErrors",otherErrors);
			
		} catch (Exception e){
			return null;
		}
		model.addAttribute("athlete", a);
		return model;
	}
}