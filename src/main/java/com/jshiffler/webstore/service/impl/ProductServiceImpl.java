package com.jshiffler.webstore.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jshiffler.webstore.domain.Product;
import com.jshiffler.webstore.repository.ProductRepository;
import com.jshiffler.webstore.service.ProductService;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	
	public void updateAllStock() {
		List<Product> allProducts = productRepository.getAllProducts();
		for(Product product : allProducts){
			if (product.getUnitsInStock()<500)
				productRepository.updateStock(product.getProductId(), product.getUnitsInStock()+1000);

		}
	}
	
	//Call the repository to update the qty of a product with a specific product ID 
	public void updateStock(String productID, long qty ) {
		
		productRepository.updateStock(productID, qty);
		
	}
		
	/* (non-Javadoc)
	 * @see com.jshiffler.webstore.service.ProductService#getProductsByCategory(java.lang.String)
	  Returns a list of products that match the category
	 */
	@Override
	public List<Product> getProductsByCategory(String category) {
		
		return productRepository.getProductsByCategory(category);
	}


	@Override
	public List <Product> getAllProducts(){

		return productRepository.getAllProducts();
	}

	@Override
	public List<Product> getProductsByFilter(String category, String manufacturer) {
		
		return productRepository.getProductsByFilter(category, manufacturer);
	}

	@Override
	public Product getProductById(String productID) {
		return productRepository.getProductById(productID);
	}

	@Override
	public List<Product> filterProducts(String category, Map<String, List<String>> filterParams) {
		
		return productRepository.filterProducts(category, filterParams);
	}

	@Override
	public void addProduct(Product product) {

		//Add the product to the repository
		productRepository.addProduct(product);
		
	}


}
