package com.jshiffler.webstore.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import com.jshiffler.webstore.service.ProductService;

@Controller
@RequestMapping("market")
public class ProductController {

	
	@Autowired
	private ProductService productService; //This is our service that allows us to reach into the product database
	
	
	//Displays all products 
	@RequestMapping("/products")
	public String List(Model model){

		model.addAttribute("products", productService.getAllProducts());

		return "products";
	}
	
	//This is used to allow products to be displayed by id
	// for example: /product?id=P1234
	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model){

		model.addAttribute("product", productService.getProductById(productId));

		return "product";
	}
	
	@RequestMapping("/products/{category}/{params}")  
	public String filterProducts(@PathVariable("category") String productCategory,@MatrixVariable(pathVar="params") Map <String,
			List<String>> filterParams ,Model model){
		System.out.println(productCategory);
		model.addAttribute("products",productService.filterProducts(productCategory, filterParams));
		return "products";
		
	}

	//Allows a query such as /products/Tablet to list all Tablets
	@RequestMapping("/products/{category}")                         //Create the mapping with a URI template variable for the product category
	public String getProductsByCategory(Model model,                
			@PathVariable("category") String productCategory){       //map the category in the URL to productCategory variable   
		model.addAttribute("products", productService.getProductsByCategory(productCategory)); //add products matching the category to the model
		
		return "products";                                          //return the name of the jsp page
	}

	//Allows a query such as /products/filter/params;brands=Google;category=Tablet	
	//Allows a search by brands and categories
	@RequestMapping("/products/filter/{params}")
	public String getProductsByFilter(@MatrixVariable(pathVar="params") Map <String, String> filterParams,Model model){
		String manufacturer = (String)filterParams.get("brands");
		String category = (String)filterParams.get("category");
		model.addAttribute("products", productService.getProductsByFilter(category,manufacturer));
		return "products";
	}
	
	//Allows the qty of a certain productID to be updated
	// /update/stock/params;id=P1234;qty=5
	@RequestMapping("/update/stock/{params}")
	public String updateStock(@MatrixVariable(pathVar="params") Map <String, String> stockParams,Model model){

		//Extract the values of the id and qty variables to feed them into the service layer.
		String productID= (String)stockParams.get("id");
		long qty = Long.parseLong(stockParams.get("qty"));
				
		productService.updateStock(productID, qty);
		return "redirect:/market/products/";
	}
	
	

}
