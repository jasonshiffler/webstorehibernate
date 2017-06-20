package com.jshiffler.webstore.service;

import java.util.List;
import java.util.Map;

import com.jshiffler.webstore.domain.Product;

//Service layer interface for Product Domain to implement business logic.

public interface ProductService {

	void updateStock(String productID, long qty);
	List <Product> getProductsByCategory(String category);
	List <Product> getAllProducts();
    List <Product> getProductsByFilter(String category, String manufacturer);
    List <Product> filterProducts(String category,Map<String, List<String>> filterParams);
    Product getProductById(String productID);
    
}
