package com.registration.sors.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.registration.sors.model.User;
import com.registration.sors.service.UserDAO;

@SuppressWarnings("javadoc")
@Controller
@RequestMapping("/testuser")
public class TestUserController {
	
	@Autowired private UserDAO dao;

	@RequestMapping(value = "/1", method = RequestMethod.GET)
	public ModelAndView test(ModelMap model) {
		int numAthletes = dao.loadAll().size();
		
		User a = new User();
		a.setFname("Max");
		dao.add(a);
		assert (false) : "hey, got a problem?";
		assert (numAthletes + 1 == dao.loadAll().size()) : "Add User Unit Test 1";
		assert (dao.find(a.getId()).getFname().equals("Max")) : "Add User Unit Test 2";
		dao.delete(a); // clean up
		return new ModelAndView("redirect:../user/list");
	}
}