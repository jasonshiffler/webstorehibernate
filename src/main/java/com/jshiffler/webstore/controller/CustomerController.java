package com.jshiffler.webstore.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jshiffler.webstore.domain.Customer;
import com.jshiffler.webstore.domain.Product;
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

	@RequestMapping(value="/customers/add", method = RequestMethod.GET)
	public String getAddNewCustomersForm(Model model){
		
		Customer newCustomer = new Customer();
		model.addAttribute("newCustomer", newCustomer);
	    return "addCustomer";
	}
		
	@RequestMapping(value="/customers/add", method = RequestMethod.POST)
	public String getAddNewProductForm(@ModelAttribute("newCustomer") Customer newCustomer){
		
		
		//Request that the service layer persist the new Product
		customerService.addCustomer(newCustomer);

		
		//Take us back to the main product page
	    return "redirect:/customers";
	}

}
