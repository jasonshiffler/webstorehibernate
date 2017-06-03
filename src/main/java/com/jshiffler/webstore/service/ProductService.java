package com.jshiffler.webstore.service;

import java.util.List;

import com.jshiffler.webstore.domain.Product;

//Service layer interface for Product Domain to implement business logic.

public interface ProductService {

	void updateAllStock();
	
	List <Product> getAllProducts();
}
