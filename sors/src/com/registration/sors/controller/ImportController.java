package com.registration.sors.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.googlecode.objectify.Key;
import com.registration.sors.handler.ImportHandler;
import com.registration.sors.service.*;
import com.registration.sors.model.Athlete;
import com.registration.sors.model.Classroom;
import com.registration.sors.model.Event;
import com.registration.sors.model.Heat;
import com.registration.sors.model.HeatSpec;
import com.registration.sors.model.Registration;
import com.registration.sors.model.School;
import com.registration.sors.model.User;


@Controller
@RequestMapping("/import")
public class ImportController {
	
	@Autowired private AthleteDAO AthleteDAO;
	@Autowired private ClassroomDAO ClassroomDAO;
	@Autowired private EventDAO EventDAO;
	@Autowired private HeatDAO HeatDAO;
	@Autowired private HeatSpecDAO HeatSpecDAO;
	@Autowired private RegistrationDAO RegistrationDAO;
	@Autowired private SchoolDAO SchoolDAO;
	@Autowired private UserDAO UserDAO;
	
	
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String getImportForm(ModelMap model, HttpSession session) {
		return "import";
	}
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(ModelMap model, HttpServletRequest req, HttpSession session) {
		// Grab table and CSV from header
		String table = (String) req.getParameter("table");
		String csv = (String) req.getParameter("csv");
		
		// Make sure the data is valid
		if (table.isEmpty() || csv.isEmpty()){
			model.addAttribute("ImporError", "Error importing file");
			return "import";
		}
		
		
		ImportHandler imp = new ImportHandler();
		
		// Pick the right table to import into
		
		if (table.equals("athlete")) {
			List<Athlete> l = imp.importAthletes(csv);
			model.addAttribute("TableData", l);
			AthleteDAO.add(l);
		} else if (table.equals("classroom")) {
			List<Classroom> l = imp.importClassrooms(csv);
			
			for (Classroom cl : l) {
				School s = SchoolDAO.find(cl.getSchoolID());
				cl.setSchool(s.getParent());
			}
			
			model.addAttribute("TableData", l);
			ClassroomDAO.add(l);
		} else if (table.equals("event")) {
			List<Event> l = imp.importEvent(csv);
			model.addAttribute("TableData", l);
			EventDAO.add(l);
		} else if (table.equals("heat")) {
			List<Heat> l = imp.importHeat(csv);
			model.addAttribute("TableData", l);
			HeatDAO.add(l);
		} else if (table.equals("heatspec")) {
			List<HeatSpec> l = imp.importHeatSpec(csv);
			model.addAttribute("TableData", l);
			HeatSpecDAO.add(l);
		} else if (table.equals("registration")) {
			List<Registration> l = imp.importRegistration(csv);
			model.addAttribute("TableData", l);
			RegistrationDAO.add(l);
		} else if (table.equals("school")) {
			List<School> l = imp.importSchool(csv);
			Key<School> key = SchoolDAO.getParentKey(1);
			
			for (School school : l) {
				school.setParent(key);
			}
			model.addAttribute("TableData", l);
			SchoolDAO.add(l);
		} else if (table.equals("user")) {
			List<User> l = imp.importUser(csv);
			model.addAttribute("TableData", l);
			UserDAO.add(l);
		}
		
		return "redirect:/athlete/list";
	}
	
}
