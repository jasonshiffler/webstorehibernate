package com.jshiffler.webstore.repository;

import java.util.List;
import java.util.Map;

import com.jshiffler.webstore.domain.Product;

//Interface for the class that builds the product repository.

public interface ProductRepository {

	List <Product> getAllProducts();                        //Return a List of all products
	List <Product> getProductsByCategory(String category);  //Return a List of products within a certain category 
	List <Product> getProductsByFilter(String category, String manufacturer); //Return a List of products based on certain filters
	Product getProductById(String productID);               //returns a product with a specific Id            
	List <Product> filterProducts(String category, Map<String,List<String>> filterParams); //returns a product within a category based on certain filters.
	void updateStock(String productId, long noOfUnits);     //Add stock for a certain product id by an amount of noOfUnits
    void addProduct(Product product);                       //Allows a new product to be added to the database	

}
