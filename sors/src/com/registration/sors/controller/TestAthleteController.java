package com.registration.sors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.registration.sors.model.Athlete;
import com.registration.sors.service.AthleteDAO;

@SuppressWarnings("javadoc")
@Controller
@RequestMapping("/testathlete")
public class TestAthleteController {
	
	@Autowired private AthleteDAO dao;

	@RequestMapping(value = "/1", method = RequestMethod.GET)
	public ModelAndView test(ModelMap model) {
		int numAthletes = dao.loadAll().size();
		
		Athlete a = new Athlete();
		a.setFname("Max");
		dao.add(a);
		assert (false) : "hey, got a problem?";
		assert (numAthletes + 1 == dao.loadAll().size()) : "Add Athlete Unit Test 1";
		assert (dao.find(a.getId()).getFname().equals("Max")) : "Add Athlete Unit Test 2";
		dao.delete(a); // clean up
		return new ModelAndView("redirect:../athlete/list");
	}
}