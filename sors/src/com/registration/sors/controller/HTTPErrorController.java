package com.registration.sors.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HTTPErrorController {

    @RequestMapping(value="/errors/error.html")
    public String handleError() {
    	return "errorPageTemplate";
    }

}
