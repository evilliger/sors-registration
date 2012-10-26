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

import com.registration.sors.helpers.Authenticator;
import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;
import com.registration.sors.service.UserDAO;

@SuppressWarnings({"javadoc", "cast"})
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired private UserDAO dao;
	
	List<String> roles = Arrays.asList("A");
	
	// Facilitates the login of a user
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		
		// Grab username and pswd from POST
		String username = (String) req.getParameter("username");
		String password = (String) req.getParameter("pswd");
		
		User u = this.dao.find(username);		
		if (!Authenticator.authenticate(u,password)) {
			model.addAttribute("AuthError", "Invalid username or password.");
			return "home";
		} else {
			session.setAttribute("system", new SystemSession(u,true));
			return "redirect:/athlete/list";
		}
	}
	
	// Facilitates the login of a user
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginUserGet(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		
		// Grab username and pswd from POST
		String username = (String) req.getParameter("username");
		String password = (String) req.getParameter("pswd");
		
		User u = this.dao.find(username);		
		if (!Authenticator.authenticate(u,password)) {
			model.addAttribute("AuthError", "Invalid username or password.");
			return "home";
		} else {
			session.setAttribute("system", new SystemSession(u,true));
			return "redirect:/athlete/list";
		}
	}
	
	// Returns the add.jsp page allowing the user to submit a new contact
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddContactPage(HttpSession session) {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		return "addUser";
	}
	
	// Dev Testing ONLY
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(HttpSession session) {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return new ModelAndView("redirect:/user/login");
		}
		// Errors will be handled here
		this.dao.init();
		return new ModelAndView("redirect:list");
	}

	// Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return new ModelAndView("redirect:/user/login");
		}
		
		User u = new User();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "user");
		binder.setRequiredFields(new String[] {"title", "fname", "lname", "pword"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
	
		// Errors will be handled here
		if (!errors.hasErrors()) {
			if(this.dao.add(u) == null) {
				throw new Exception ("Error adding contact to datastore");
			}
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Displays the get.jsp page with the information from the contact
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateContactPage(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			return "redirect:/user/login";
		}
		
		User u = new User();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "user");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here
		if (!errors.hasErrors()) {
			u = this.dao.find(u.getId());
			model.addAttribute("user", u);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "updateUser";
	}
	
	// Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login";
		}
		
		User u = new User();
		
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "user");
		binder.setRequiredFields(new String[] {"id","title", "fname", "lname", "pword"});
		binder.bind(req);
		BindingResult errors = binder.getBindingResult();
		
		// Errors will be handled here		
		if (!errors.hasErrors()) {
			this.dao.update(u);
		} else {
			throw new Exception ("Supply ID, Name, and Email");
		}
		
		// return to list
		return "redirect:list";

	}
	
	// Deletes a contact based on information submitted
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String delete(HttpServletRequest req, HttpSession session) throws Exception {
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login";
		}
		
		User u = new User();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "user");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		
		// Errors will be handled here		
		this.dao.delete(u);

		// return to list
		return "redirect:list";

	}

	// get all contacts
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listContact(ModelMap model, HttpSession session) {	
		SystemSession ss = (SystemSession)session.getAttribute("system");
		if(!Authenticator.isAuthenticated(this.roles, ss)){
			session.invalidate();
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login";
		}
		
		// Errors will be handled here
		model.addAttribute("userList", this.dao.loadAll());
		return "listUser";
	}
}