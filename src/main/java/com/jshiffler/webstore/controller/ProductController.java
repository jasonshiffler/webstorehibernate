package com.jshiffler.webstore.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jshiffler.webstore.domain.Product;
import com.jshiffler.webstore.repository.ProductRepository;
import com.jshifler.webstore.service.ProductService;

@Controller
public class ProductController {

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private ProductService productService;
	
	@RequestMapping("/products")
	public String List(Model model){

		model.addAttribute("products", productRepository.getAllProducts());

		return "products";
	}

	@RequestMapping("/update/stock")
	public String updateStock(Model model){

		productService.updateAllStock();
		return "redirect:/products/";
	}
	
	

}
