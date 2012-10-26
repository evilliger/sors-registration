package com.registration.sors.controller;

import javax.mail.Session;
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
import com.registration.sors.model.User;
import com.registration.sors.service.UserDAO;

@SuppressWarnings("javadoc")
@Controller
@RequestMapping("/user")
public class UsersController {
	
	@Autowired private UserDAO dao;
	
	// Facilitates the login of a user
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req, ModelMap model) throws Exception {
		
		// Grab username and pswd from POST
		String username = (String) req.getParameter("username");
		String password = (String) req.getParameter("pswd");
		
		User u = dao.find(username);		
		if (u == null) {
			model.addAttribute("AuthError", "Invalid username or password.");
			return "home";
		} else {
			if(u.getPword().equals(password)){
				//session.setAttribute("user", u);
				return "redirect:/athlete/list";
			}else{
				model.addAttribute("AuthError", "Invalid username or password.");
				return "home";
			}
		}
	}
	
	// Returns the add.jsp page allowing the user to submit a new contact
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddContactPage(ModelMap model) {
		return "addUser";
	}
	
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(ModelMap model) {
		dao.init();
		return new ModelAndView("redirect:list");
	}

	// Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest req, ModelMap model) throws Exception {

		User u = new User();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "user");
		binder.setRequiredFields(new String[] {"title", "fname", "lname", "pword"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			if(dao.add(u) == null) {
				throw new Exception ("Error adding contact to datastore");
			}
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Displays the get.jsp page with the information from the contact
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateContactPage(HttpServletRequest req, ModelMap model) throws Exception {

		User u = new User();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "user");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			u = dao.find(u.getId());
			model.addAttribute("user", u);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "updateUser";
	}
	
	// Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, ModelMap model) throws Exception {

		User u = new User();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "user");
		binder.setRequiredFields(new String[] {"id","title", "fname", "lname", "pword"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		if (!errors.hasErrors()) {
			dao.update(u);
		} else {
			throw new Exception ("Supply ID, Name, and Email");
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Deletes a contact based on information submitted
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, ModelMap model) throws Exception {

		User u = new User();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "user");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		dao.delete(u);

		// return to list
		return "redirect:list";

	}

	// get all contacts
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listContact(ModelMap model) {	
		model.addAttribute("userList", dao.loadAll());
		return "listUser";
	}
}