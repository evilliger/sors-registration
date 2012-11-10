package com.registration.sors.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
			model.addAttribute("error", "No data to import!");
			return "import";
		}
		
		
		ImportHandler imp = new ImportHandler();
		
		// Pick the right table to import into
		
		if (table.equals("athlete")) {
			try {
				List<Athlete> l = imp.importAthletes(csv);
				model.addAttribute("success", l.size() + " athlete(s) imported successfully");
				AthleteDAO.add(l);
			} catch (Exception e) {
				model.addAttribute("error", "An error occurred adding data.");
				return "import";
			}
			
		} else if (table.equals("classroom")) {
			try {
				List<Classroom> l = imp.importClassrooms(csv);
				model.addAttribute("success", l.size() + " classroom(s) imported successfully");
				ClassroomDAO.add(l);
			} catch (Exception e) {
				model.addAttribute("error", "An error occurred adding data.");
				return "import";
			}
			
		} else if (table.equals("event")) {
			try {
				List<Event> l = imp.importEvent(csv);
				model.addAttribute("success", l.size() + " event(s) imported successfully");
				EventDAO.add(l);
			} catch (Exception e) {
				model.addAttribute("error", "An error occurred adding data.");
				return "import";
			}
			
		} else if (table.equals("heat")) {
			try {
				List<Heat> l = imp.importHeat(csv);
				model.addAttribute("success", l.size() + " heat(s) imported successfully");
				HeatDAO.add(l);
			} catch (Exception e) {
				model.addAttribute("error", "An error occurred adding data.");
				return "import";
			}
			
		} else if (table.equals("heatspec")) {
			try {
				List<HeatSpec> l = imp.importHeatSpec(csv);
				model.addAttribute("success", l.size() + " HeatSpec(s) imported successfully");
				HeatSpecDAO.add(l);
			} catch (Exception e) {
				model.addAttribute("error", "An error occurred adding data.");
				return "import";
			}
			
		} else if (table.equals("registration")) {
			try {
				List<Registration> l = imp.importRegistration(csv);
				model.addAttribute("success", l.size() + " registration(s) imported successfully");
				RegistrationDAO.add(l);
			} catch (Exception e) {
				model.addAttribute("error", "An error occurred adding data.");
				return "import";
			}
		} else if (table.equals("school")) {
			try {
				List<School> l = imp.importSchool(csv);
				model.addAttribute("success", l.size() + " school(s) imported successfully");
				SchoolDAO.add(l);
			} catch (Exception e) {
				model.addAttribute("error", "An error occurred adding data.");
				return "import";
			}
		} else if (table.equals("user")) {
			try {
				List<User> l = imp.importUser(csv);
				model.addAttribute("success", l.size() + " user(s) imported successfully");
				UserDAO.add(l);
			} catch (Exception e) {
				model.addAttribute("error", "An error occurred adding data.");
				return "import";
			}
		}
		
		return "import";
	}
	
}
