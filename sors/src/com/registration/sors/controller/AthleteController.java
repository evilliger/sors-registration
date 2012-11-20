//-------------------------------------------//
// Name: AthleteController					 //
// Purpose: Spring Cotroller				 //
//-------------------------------------------//
package com.registration.sors.controller;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

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
import com.registration.sors.model.AthData;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.Event;
import com.registration.sors.model.Registration;
import com.registration.sors.model.School;
import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;
import com.registration.sors.service.AthleteDAO;
import com.registration.sors.service.ClassroomDAO;
import com.registration.sors.service.EventConflictDAO;
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
	@Autowired private EventConflictDAO EvConfdao;
	@Autowired private SchoolDAO Schdao;
	@Autowired private ClassroomDAO Cladao;
	@Autowired private RegistrationDAO Regdao;
	
	List<String> roles = Arrays.asList("A", "T");
	
	static Logger log = Logger.getLogger(AthleteController.class.getName());
	
	// Name: getAddAthletePage
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current user session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		add page - page allowing the user to submit a new athlete
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddAthletePage(HttpSession session, HttpServletRequest req, ModelMap model) {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Security.isAuthenticated(this.roles, ss)){
			session.invalidate();	
			return "redirect:/user/login"; 
		}
		
		model = loadModel(model, req, true, ss.getUser(), new Athlete(), null,null);
		
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
		binder.setRequiredFields(new String[] {"fname", "lname", "gender", "bdate", "classroomId"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		RegData regData = handleRegistrations(req, a);
		
		//Errors will be handled here
		if (!errors.hasErrors() && regData.errors.isEmpty()) {
			try {
				if((a = this.Athdao.add(a)) == null) 
					return "errorPageTemplate";
				else 
					log.warning(ss.getUser().getEmail() + " added " + a.getFname() + " " + a.getLname() + " to the datastore");
			} catch (Exception e) {
				log.severe("Error adding athlete to datastore: " + e.toString());
				return "errorPageTemplate";
			}
			for(int i = 0; i < regData.registrations.size(); ++i){
				Registration r = regData.registrations.get(i);
				r.setAthleteID(a.getId());
				if(this.Regdao.add(r) != null)
					log.warning(ss.getUser().getEmail() + " added " + regData.registrations.size() + " to the datastore");
			}
		} else {
			model = loadModel(model, req, true,ss.getUser(),a,errors,regData.errors);
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
		
		Long cId = Long.parseLong(req.getParameter("classroomid"));
		try {
			Classroom c = this.Cladao.find(cId);
			a = this.Athdao.find(a.getId(), c);
		} catch (Exception e) {
			log.severe("Error finding athlete: " + e.toString());
			return "errorPageTemplate";
		}
		if (errors.hasErrors()) 
			return "redirect:list";
		
		model = loadModel(model,req,false,ss.getUser(),a,errors,null);

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
		binder.setRequiredFields(new String[] {"id", "fname", "lname", "gender", "bdate"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		RegData regData = handleRegistrations(req, a);
		
		if (!errors.hasErrors() && regData.errors.isEmpty()) {
			try {
				if((a = this.Athdao.add(a)) == null) 
					return "errorPageTemplate";
				else 
					log.warning(ss.getUser().getEmail() + " updated " + a.getFname() + " " + a.getLname() + " in the datastore");
			} catch (Exception e) {
				log.severe("Error adding athlete to datastore: " + e.toString());
				return "errorPageTemplate";
			}
			for(int i = 0; i < regData.registrations.size(); ++i){
				Registration r = regData.registrations.get(i);
				r.setAthleteID(a.getId());
				try {
					if(this.Regdao.add(r) != null)
						log.warning(ss.getUser().getEmail() + " added " + regData.registrations.size() + " to the datastore");	
				} catch (Exception e) {
					log.severe("Error adding registration: " + e.toString());
					return "errorPageTemplate";
				}
			}
		} else {
			model = loadModel(model,req,false,ss.getUser(),a,errors,regData.errors);
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

		Long cId = Long.parseLong(req.getParameter("classroomid"));
		try {
			Classroom c = this.Cladao.find(cId);
			a = this.Athdao.find(a.getId(), c);
		} catch (Exception e) {
			log.severe("Error finding athlete: " + e.toString());
			return "errorPageTemplate";
		}
	
		try {
			List<Registration> regList = Regdao.find(a);
			for (Registration reg : regList) { // delete existing registration entries.
				Registration r = new Registration();
				r.setId(reg.getId());
				Regdao.delete(r);
			}
		} catch (Exception e) {
			log.severe("Error deleting registrations: " + e.toString());
			return "errorPageTemplate";
		}
		
		if(errors.hasErrors() || this.Athdao.delete(a) == null)
			return "errorPageTemplate";
		else 
			log.warning(ss.getUser().getEmail() + " deleted " + a.getFname() + " " + a.getLname() + " from the datastore");
		
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
		
		List<AthData> athdata = new ArrayList<AthData>();
		
		List<Athlete> athletes;
		try {
			if(ss.getUser().getRole().equals("T")) {
				athletes = this.Athdao.loadAll(ss.getUser());
				model.addAttribute("title",this.Cladao.find(ss.getUser()).getClassName());
			}
			else {
				athletes = this.Athdao.loadAll();
				model.addAttribute("title","Athletes 2012");
			}
		} catch (Exception e) {
			log.severe("Error loading list of athletes: " + e.toString());
			return "errorPageTemplate";
		}
		for (Athlete a : athletes){
			String event1 = "None";
			String event2 = "None";
			List<Registration> regs = this.Regdao.find(a);
			if(regs != null){
				if(regs.size() > 0) 
					if(Evdao.find(regs.get(0).getEventID()) != null)
						event1 = Evdao.find(regs.get(0).getEventID()).getName();
				if(regs.size() > 1)
					if(Evdao.find(regs.get(1).getEventID()) != null)
						event2 = Evdao.find(regs.get(1).getEventID()).getName();
			}
			
			boolean complete = !(a.getFname() == null || a.getFname().equals("") || a.getBdate() == null ||
					a.getLname() == null || a.getLname().equals("") ||
					a.getLname() == null || a.getLname().equals("") || 
					a.getMname() == null || a.getMname().equals("") || 
					a.getGender() == null || a.getGender().equals("") ||
					event1.equals("None") && event2.equals("None"));
			
			athdata.add(new AthData(a,complete,"",event1,event2));
		}

		model.addAttribute("athleteList", athdata);
		return "listAthlete";
	}
	
	// ********* HELPER METHODS *******************
	
	// Returns a lists of registrations and errors associated with them inside of a class called RegData
	private RegData handleRegistrations(HttpServletRequest req, Athlete a) {
		List<String> errors = new ArrayList<String>();
		List<Registration> registrations = new ArrayList<Registration>();
		Long first = -1L;
		if (req.getParameter("pevent") != null && !((String)req.getParameter("pevent")).equals("-1")) {
			// add new registration (primary event added)
			first = Long.parseLong((String)req.getParameter("pevent"));
			Event pe = this.Evdao.find(first);
			if(pe != null){
				Double pscore = 0.0;
				try{
					pscore = Double.parseDouble((String)req.getParameter("pscore"));
					if (pscore > pe.getMax() || pscore < pe.getMin()) {
						errors.add("Primary event score needs to be between " + pe.getMin() + " and " + pe.getMax() + ".");
					} else {
						Registration r = new Registration(pscore, a.getId(), pe.getId());
						Long regid = -1L;
						if (req.getParameter("pregid") != null && !((String)req.getParameter("pregid")).equals("-1")) {
							try {
								regid = Long.parseLong((String)req.getParameter("pregid"));
							} catch (Exception e){
								errors.add("Registration ID in incorrect format.");
							}
						}
						if(regid != -1) {
							r.setId(regid);
						}
						registrations.add(r);
					}
				} catch (Exception e){
					errors.add("Please enter numeric score for primary event.");
				}
			} else {
				errors.add("Primary event not found.");
			}
		} else if(req.getParameter("pevent") != null && ((String)req.getParameter("pevent")).equals("-1")){
			// delete registration entry for pevent
			Long regid = -1L;
			if (req.getParameter("pregid") != null && !((String)req.getParameter("pregid")).equals("-1")) {
				try {
					regid = Long.parseLong((String)req.getParameter("pregid"));
					Registration r = new Registration();
					r.setId(regid);
					Regdao.delete(r);
				} catch (Exception e){
					errors.add("Registration ID in incorrect format.");
				}
			}
		}
		
		if (req.getParameter("sevent") != null && !((String)req.getParameter("sevent")).equals("-1")) {
			Long second = Long.parseLong((String)req.getParameter("sevent"));
			if(first.equals(second))
				errors.add("Cannot register twice for the same event.");
			else {
				Event sse = this.Evdao.find(second);
				if(sse != null){
					Event pe = this.Evdao.find(Long.parseLong((String)req.getParameter("pevent")));
					errors = handleConflictingEvents(pe, sse, errors);
					Double sscore = 0.0;
					try {
						sscore = Double.parseDouble((String)req.getParameter("sscore"));
						if (sscore > sse.getMax() || sscore < sse.getMin()) {
							errors.add("Secondary event score needs to be between " + sse.getMin() + " and " + sse.getMax() + ".");
						} else {
							Registration r = new Registration(sscore, a.getId(), sse.getId());
							Long regid = -1L;
							if (req.getParameter("sregid") != null && !((String)req.getParameter("sregid")).equals("-1")) {
								try {
									regid = Long.parseLong((String)req.getParameter("sregid"));
								} catch (Exception e){
									errors.add("Registration ID in incorrect format.");
								}
							}
							if(regid != -1) {
								r.setId(regid);
							}
							registrations.add(r);
						}
					} catch(Exception e){
						errors.add("Please enter numeric score for secondary event.");
					}
				} else {
					errors.add("Secondary event not found.");
				}
			}
		} else if(req.getParameter("sevent") != null && ((String)req.getParameter("sevent")).equals("-1")){
			Long regid = -1L;
			if (req.getParameter("sregid") != null && !((String)req.getParameter("sregid")).equals("-1")) {
				try {
					regid = Long.parseLong((String)req.getParameter("sregid"));
					Registration r = new Registration();
					r.setId(regid);
					Regdao.delete(r);
				} catch (Exception e){
					errors.add("Registration ID in incorrect format.");
				}
			}
		}
		if (a.getClassroomId() == -1)
			errors.add("Please specify a classroom.");
		return new RegData(registrations, errors);
	}
	
	// Adds errors to the list of errors for conflicting events.
	private List<String> handleConflictingEvents(Event primary, Event secondary, List<String> errors) {
		List<Long> conflicts = EvConfdao.find(primary);
		for (Long eId : conflicts) {
			if (eId.equals(secondary.getId()))
					errors.add(primary.getName() + " conflicts with " + secondary.getName() + ". Please remove one of them.");
		}
		return errors;
	}
	
	// Loads the model for the maintain athletes jsp
	private ModelMap loadModel(ModelMap model, HttpServletRequest req, boolean add, User u, Athlete a, BindingResult errors, List<String> otherErrors) {
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
				if (!add) {
					Classroom c = this.Cladao.find(a.getClassroomId());
					model.addAttribute("classroom", c.getId());
					model.addAttribute("school", c.getSchoolID());
				}
			} else { 
				Classroom c = this.Cladao.find(u);
				model.addAttribute("classroom", c.getId());
				model.addAttribute("school", c.getSchoolID());
				a.setClassroomId(c.getId());
			}
			
			try{
				if(req.getParameter("pevent")!=null)
					model.addAttribute("pevent", Long.parseLong(req.getParameter("pevent")));
				else model.addAttribute("pevent", -1L);
			} catch (Exception e){
				model.addAttribute("pevent", -1L);
			}
			
			try {
				if(req.getParameter("sevent")!=null)
					model.addAttribute("sevent", Long.parseLong(req.getParameter("sevent")));
				else model.addAttribute("sevent", -1L);
			} catch (Exception e){
				model.addAttribute("sevent", -1L);
			}    
			
			try {
				if(req.getParameter("pscore")!=null)
					model.addAttribute("pscore", Double.parseDouble(req.getParameter("pscore")));
				else model.addAttribute("pscore", -1D);
			} catch (Exception e){
				model.addAttribute("pscore", -1D);
			}  
			
			try {
				if(req.getParameter("sscore")!=null)
					model.addAttribute("sscore", Double.parseDouble(req.getParameter("sscore")));
				else model.addAttribute("sscore", -1D);
			} catch (Exception e){
				model.addAttribute("sscore", -1D);
			}  
			
			List<Registration> regs = this.Regdao.find(a);
			if(regs != null && regs.size() > 0) {
				model.addAttribute("pregId", regs.get(0).getId());
				if(!add) {
					model.addAttribute("pevent", regs.get(0).getEventID());
					model.addAttribute("pscore", regs.get(0).getScore());
				}
			} else {
				model.addAttribute("pregId", -1L);
			}
			
			if(regs != null && regs.size() > 1) {
				model.addAttribute("sregId", regs.get(1).getId());
				if(!add){
					model.addAttribute("sevent", regs.get(1).getEventID());
					model.addAttribute("sscore", regs.get(1).getScore());
				}
			} else {
				model.addAttribute("sregId", -1L);
			}
			
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
	
	// Special binder to customize the output of the date
	@InitBinder
	public void binder(WebDataBinder binder) {binder.registerCustomEditor(Date.class,
	    new PropertyEditorSupport() {
	        public String getAsText() {
	        	Object val;
				if((val = getValue()) != null)
					return new SimpleDateFormat("MM/dd/yyyy").format(val);
				else 
					return "";
        	}
	    });
	}
}

// Class that holds registrations and errors associated with them
class RegData {
	public List<Registration> registrations;
	public List<String> errors;

	public RegData(List<Registration> registrations, List<String> errors) {
		this.registrations = registrations;
		this.errors = errors;
	}
}