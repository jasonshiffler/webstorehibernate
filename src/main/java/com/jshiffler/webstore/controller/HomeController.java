package com.jshiffler.webstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/") 
public class HomeController {

	@RequestMapping                      //Default mapping method
	public String welcome(Model model){
		
		//Add attributes to the model
		model.addAttribute("greeting","Welcome to the Web Store");
		model.addAttribute("tagline","The one and only amazing web store");
		
		//direct requests to the welcome.jsp page.
		return "welcome";
	}
	
	
}
