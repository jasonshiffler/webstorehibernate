package com.jshiffler.webstore.controller;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.jshiffler.webstore.domain.Product;
import com.jshiffler.webstore.exception.NoProductsFoundUnderCategoryException;
import com.jshiffler.webstore.exception.ProductNotFoundException;
import com.jshiffler.webstore.service.ProductService;

@Controller
@RequestMapping("market")
public class ProductController {

	// This is our product service layer 
	@Autowired
	private ProductService productService; 

		
	// Tells Spring which fields are allowed for form binding to prevent bogus data from being passed 
	// in
	@InitBinder
	public void initialiseBinder(WebDataBinder binder){

		// Determines which fields can be bound to the domain object from the httpservlet

		binder.setAllowedFields("productId", "name", "unitPrice",
				"description", "manufacturer", "category",
				"unitsInStock", "condition", "productImage");
	}

	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, 
			ProductNotFoundException exception){
		ModelAndView mav = new ModelAndView();
		mav.addObject("invalidProductId", exception.getProductId());
		mav.addObject("exception", exception);
		mav.addObject("url",
		req.getRequestURL()+"?"+req.getQueryString());
		mav.setViewName("productNotFound");
		return mav;
	}
	
	
	
	// Displays all products
	// Sample URL: /products
	@RequestMapping("/products")
	public String List(Model model){

		//Request all products from the service layer and pass the results to the model
		model.addAttribute("products", productService.getAllProducts());

		//Let the View Resolver know what view page to use
		return "products";
	}

	// This is used to allow products to be displayed by id
	// Sample URL: /product?id=P1234
	@RequestMapping("/product")
	public String getProductById(@RequestParam("id") String productId, Model model){

		// Request a product with a particular id from the service layer and add it to the model
		model.addAttribute("product", productService.getProductById(productId));

		// Let the View Resolver know what view page to use
		return "product";
	}

	@RequestMapping("/products/{category}/{params}")  
	public String filterProducts(@PathVariable("category") String productCategory,@MatrixVariable(pathVar="params") Map <String,
			List<String>> filterParams ,Model model){

		// Request a product with a particular category  and add it to the model
		model.addAttribute("products",productService.filterProducts(productCategory, filterParams));

		// Let the View Resolver know what view page to use
		return "products";
	}

	// Allows a query such as /products/Tablet to list all Tablets
	@RequestMapping("/products/{category}")                         //Create the mapping with a URI template variable for the product category
	public String getProductsByCategory(Model model,                
			@PathVariable("category") String productCategory){       //map the category in the URL to productCategory variable   

		// Grab a list of the products from the service layer that match the requested
		// category
		List<Product> products = productService.getProductsByCategory(productCategory);
		
		// If we didn't find a product matching that category throw an exception
				if (products == null || products.isEmpty()) {
			        throw new NoProductsFoundUnderCategoryException();
				}
				
		// Request all products in a category from the service layer and add it to the model
		model.addAttribute("products", products); 

		// Let the View Resolver know what view page to use
		return "products";                                         
	}

	// Allows a search by brands and categories
	// Sample URL: /products/filter/params;brands=Google;category=Tablet	
	@RequestMapping("/products/filter/{params}")
	public String getProductsByFilter(@MatrixVariable(pathVar="params") Map <String, String> filterParams,Model model){

		// Extract the values from the K/V pairs and assign them to strings
		String manufacturer = (String)filterParams.get("brands");
		String category = (String)filterParams.get("category");

		// Request all products of the same category from a particular manufacturer from the service layer and add it to the model
		model.addAttribute("products", productService.getProductsByFilter(category,manufacturer));

		// Let the View Resolver know what view page to use
		return "products";
	}

	// Allows the qty of a certain productID to be updated
	// Sample URL: /update/stock/params;id=P1234;qty=5
	@RequestMapping("/update/stock/{params}")
	public String updateStock(@MatrixVariable(pathVar="params") Map <String, String> stockParams,Model model){

		// Extract the values of the id and qty variables
		String productID= (String)stockParams.get("id");
		long qty = Long.parseLong(stockParams.get("qty"));

		// Request that the service add a certain qty to a particular product id
		productService.updateStock(productID, qty);

		// Take us back to the main product page
		return "redirect:/market/products/";
	}

	// Used to allow new products to be added
	@RequestMapping(value="/products/add", method = RequestMethod.GET)
	public String getAddNewProductForm(Model model){

		Product newProduct = new Product();
		model.addAttribute("newProduct", newProduct);
		return "addProduct";
	}

	// Gets called once the add product form is submitted
	@RequestMapping(value="/products/add", method = RequestMethod.POST)
	public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct, BindingResult result, HttpServletRequest request){

		// Throw an error if disallowed fields are being bound
		if (result.getSuppressedFields().length > 0){
			throw new RuntimeException("Attempting to bind disallowed fields: "+ 
					StringUtils.arrayToCommaDelimitedString(result.getSuppressedFields())
					); 
		}

		// Get Image file that's being uploaded
		MultipartFile productImage = newProduct.getProductImage();
		
		// Get the root directory of the app
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
				
		// If there is an image file
		if (productImage !=null && !productImage.isEmpty()){
			
			try{
			    // Save the image file to the appropriate location
				productImage.transferTo(new File(rootDirectory+"resources/images/"+newProduct.getProductId() + ".jpg"));
			} catch (Exception e) {
				
				// Throw an error if we couldn't save the image 
				
				throw new RuntimeException("Product Image saving failed", e);
			}
		}

	// Request that the service layer persist the new Product
	productService.addProduct(newProduct);

	// Take us back to the main product page
	return "redirect:/market/products";
}

}
