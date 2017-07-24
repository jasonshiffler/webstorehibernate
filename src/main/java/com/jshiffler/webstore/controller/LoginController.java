package com.jshiffler.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// This controller handles requests for the authentication/login page

@Controller
public class LoginController {

	
	@RequestMapping(value= "/login", method = RequestMethod.GET)
	public String login() {
		return "login";
	}
		
}
