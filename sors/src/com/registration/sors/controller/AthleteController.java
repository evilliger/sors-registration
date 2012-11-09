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
import org.springframework.web.servlet.ModelAndView;

import com.registration.sors.helpers.Security;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.Event;
import com.registration.sors.model.School;
import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;
import com.registration.sors.service.AthleteDAO;
import com.registration.sors.service.ClassroomDAO;
import com.registration.sors.service.EventDAO;
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
	
	List<String> roles = Arrays.asList("A", "T");

	// Dev Testing ONLY
	// Name: init
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current user session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - if user is logged in and authorized
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(HttpSession session) {
		
		School s = new School();
		s.setGroupID("012");
		s.setId(new Long(1));
		s.setName("Bobby Jones");
		s.setVolunteerNum(1242);
		Schdao.add(s);
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return new ModelAndView("redirect:/user/login");
		}
		this.Athdao.init();
		return new ModelAndView("redirect:list");
	}
	
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
		
		model = loadModel(model, true, ss.getUser(), new Athlete(), null);
		
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
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login";
		}
		
		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"fname", "lname", "mname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		//Errors will be handled here
		if (!errors.hasErrors()) {
			if(this.Athdao.add(a) == null) {
				return "errorPageTemplate";
			}
		} else {
			model = loadModel(model,true,ss.getUser(),a,errors);
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
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		a = Athdao.find(a.getId());
		List<Event> events = this.Evdao.loadAll();
		List<School> schools = this.Schdao.loadAll();
		List<Classroom> classrooms = this.Cladao.loadAll();

		model.addAttribute("athlete", a);
		// Errors will be handled here
		if (errors.hasErrors()) {
			model.addAttribute("user",ss.getUser());
			model.addAttribute("events", events);
			model.addAttribute("schools", schools);
			model.addAttribute("classrooms",classrooms);
			model.addAllAttributes(errors.getModel()); 
			return "maintainAthlete";
		}
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
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

		Athlete a = new Athlete();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id", "fname", "lname", "mname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		List<Event> events = this.Evdao.loadAll();
		List<School> schools = this.Schdao.loadAll();
		List<Classroom> classrooms = this.Cladao.loadAll();
		List<String> scores = CheckScores(req);
		
		// Errors will be handled here
		if (!errors.hasErrors()) {
			this.Athdao.update(a);
		} else {
			model.addAttribute("scores",scores);
			model.addAttribute("user",ss.getUser());
			model.addAttribute("events", events);
			model.addAttribute("schools", schools);
			model.addAttribute("classrooms",classrooms);
			model.addAttribute("athlete", a);
			model.addAllAttributes(errors.getModel()); 
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
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}

		Athlete a = new Athlete();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(a, "athlete");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		//BindingResult errors = binder.getBindingResult();
		// Errors will be handled here
		this.Athdao.delete(a);

		// return to list
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
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		
		// Errors will be handled here
		model.addAttribute("athleteList", this.Athdao.loadAll());
		return "listAthlete";
	}
	
	// Returns a list of errors for when the entered scores for the events are either
	// - not numeric
	// - less than minScore or greater than maxScore
	public List<String> CheckScores(HttpServletRequest req) {
		List<String> scores = new ArrayList<String>();
		try {
			String pevent = (String)req.getAttribute("pevent");
			String ssevent = (String)req.getAttribute("ssevent");
			if (pevent != null) {
				Event pe = this.Evdao.find(Long.parseLong(pevent));
				Double pscore = Double.parseDouble((String)req.getAttribute("pscore"));
				if (pscore > pe.getMax() || pscore < pe.getMin())
					throw new Exception("Value not within bounds");
			}
			if (ssevent != null) {
				Event sse = this.Evdao.find(Long.parseLong(ssevent));
				Double sscore = Double.parseDouble((String)req.getAttribute("sscore"));
				if (sscore > sse.getMax() || sscore < sse.getMin())
					throw new Exception("Value not within bounds");
			}
		} catch (Exception e) {
			scores.add("Invalid score.");
		}
		return scores;
	}
	
	// ********* HELPER METHODS *******************s
	private ModelMap loadModel(ModelMap model, boolean add, User u, Athlete a, BindingResult errors) {
		
		List<Event> events = this.Evdao.loadAll();
		List<School> schools = this.Schdao.loadAll();
		List<Classroom> classrooms = this.Cladao.loadAll();
		
		model.addAttribute("user",u);
		model.addAttribute("events",events);
		model.addAttribute("schools",schools);
		model.addAttribute("classrooms",classrooms);
		model.addAttribute("athlete", a);
		model.addAttribute("add", add);
		
		if(errors != null)
			model.addAllAttributes(errors.getModel()); 
		
		return null;
	}
}