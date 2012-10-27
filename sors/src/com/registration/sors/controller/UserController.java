//-------------------------------------------//
// Name: UserController		    			 //
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
import com.registration.sors.model.SystemSession;
import com.registration.sors.model.User;
import com.registration.sors.service.UserDAO;

@Controller
@RequestMapping("/user")
//-------------------------------------------//
//Name: UserController						 //
//Purpose: Implement the functionality of 	 //
//		adding, deleting, updating, and 	 //
//		listing all of the users. Uses the   //
//		userdao to accomplish its tasks	     //
//-------------------------------------------//
public class UserController {
	
	@Autowired private UserDAO dao;
	
	List<String> roles = Arrays.asList("A");
	
	// Name: loginUser
	// Purpose: Facilitates the login of a user
	// Parameters: session - the current user session
	//			req - the login data
	//			model - the model for the home page
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		add page - the list of athletes
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		
		// Grab username and pswd from POST
		String username = (String) req.getParameter("username");
		String password = (String) req.getParameter("pswd");
		
		User u = this.dao.find(username);		
		if (!Security.authenticate(u,password)) {
			model.addAttribute("AuthError", "Invalid username or password.");
			return "home";
		} else {
			session.setAttribute("system", new SystemSession(u,true));
			return "redirect:/athlete/list";
		}
	}
	
	// Name: loginUserGet
	// Purpose: Facilitates the login of a user
	// Parameters: session - the current user session
	//			req - the login data
	//			model - the model for the home page
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		add page - the list of athletes
	 
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginUserGet(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		
		// Grab username and pswd from POST
		String username = (String) req.getParameter("username");
		String password = (String) req.getParameter("pswd");
		
		User u = this.dao.find(username);
		
		if (!Security.authenticate(u,password)) {
			model.addAttribute("AuthError", "Invalid username or password.");
			return "home";
		} else {
			session.setAttribute("system", new SystemSession(u,true));
			return "redirect:/athlete/list";
		}
	}
	
	// Name: loginUserGet
	// Purpose: Facilitates the logout of a user
	// Parameters: session - the current user session
	//			req - the login data
	//			model - the model for the home page
	// Return: page redirect
	//		login page
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logoutUser(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		session.invalidate();
		return "home";
	}
	
	// Name: getAddUserPage
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current user session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		add page - page allowing the user to submit a new user

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddUserPage(HttpSession session) {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
		
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login"; 
		}
		return "addUser";
	}


	// Dev Testing ONLY
	// Name: init
	// Purpose: Determine if the user is logged in and authorized 
	//		to see this page.
	// Parameters: session - the current user session
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - list of users
	
	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public ModelAndView init(HttpSession session) {
		// Errors will be handled here
		this.dao.init();
		return new ModelAndView("redirect:list");
	}

	// Name: add
	// Purpose: Receives the submission of add.jsp stores it in the datastore and redirects to list.jsp
	// Parameters: session - the current user session
	//				req - the data to add
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - when data is added
	
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public ModelAndView add(HttpServletRequest req, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
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
				throw new Exception ("Error adding User to datastore");
			}
			
		} else {
			throw new Exception ("Supply required information.");
		}
		return new ModelAndView("redirect:list");

	}
	
	// Name: getUpdateUserPage
	// Purpose: Displays the get.jsp page with the information from the User
	// Parameters: session - the current user session
	//			req - user to update
	//			model - the model for update
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		updateuser page - page to edit user information

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String getUpdateUserPage(HttpServletRequest req, HttpSession session, ModelMap model) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
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
			Long i = u.getId();
			u = this.dao.find(u.getId());
			model.addAttribute("user", u);
		} else {
			throw new Exception ("Helpful exception code");
		}
		return "updateUser";
	}
	// Name: update
	// Purpose: Accepts the input from update.jsp, stores it in the datastore, and returns list.jsp
	// Parameters: session - the current user session
	//				req - the entry to update
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - when data is updated
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(HttpServletRequest req, HttpSession session) throws Exception {
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
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
	
	// Name: delete
	// Purpose: Deletes a User based on information submitted
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
		
		User u = new User();
		ServletRequestDataBinder binder = new ServletRequestDataBinder(u, "user");
		binder.setRequiredFields(new String[] {"id"});
		binder.bind(req);
		
		// Errors will be handled here		
		this.dao.delete(u);

		// return to list
		return "redirect:list";

	}

	// Name: list
	// Purpose: get a list of all users
	// Parameters: session - the current user session
	//				model - the model for list
	// Return: page redirect
	//		login page - if user not logged in or not authorized
	//		list page - list of users
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listUsers(ModelMap model, HttpSession session) {	
		
		SystemSession ss = (SystemSession)session.getAttribute("system");
		
		if(!Security.isAuthenticated(this.roles, ss)){
			
			session.invalidate();
			
			// Right now it takes the user back to the Login Page no matter what
			return "redirect:/user/login";
		}
		
		// Errors will be handled here
		
		List<User> list = this.dao.loadAll();
		list.remove(0);
		model.addAttribute("userList",list);
		return "listUser";
	}
}