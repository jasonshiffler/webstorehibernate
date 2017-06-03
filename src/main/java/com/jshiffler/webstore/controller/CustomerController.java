package com.jshiffler.webstore.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jshiffler.webstore.service.CustomerService;


@Controller
public class CustomerController {

	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/customers")
	public String List(Model model){

		model.addAttribute("customers", customerService.getAllCustomers());

		return "customers";
	}

		
	

}
