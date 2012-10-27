//-------------------------------------//
// Name: HomeComtroller                //
// Purpose: return the home screen     //
//-------------------------------------//
package com.registration.sors.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
//-------------------------------------//
//Name: HomeComtroller                //
//Purpose: return the home screen     //
//-------------------------------------//
public class HomeController {

	// Name: Home
	// Purpose: To return the home screen
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		return "home";
	}
}
