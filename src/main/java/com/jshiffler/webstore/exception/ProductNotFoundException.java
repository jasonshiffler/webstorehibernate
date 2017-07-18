package com.jshiffler.webstore.exception;

public class ProductNotFoundException extends RuntimeException {
	
	
	private static final long serialVersionUId = -23823842890L;
	
	private String productId;
	
	public ProductNotFoundException(String productId){
		this.productId = productId;
	}

	/**
	 * @return the productId
	 */
	public String getProductId() {
		return productId;
	}

	/**
	 * @param productId the productId to set
	 */
	public void setProductId(String productId) {
		this.productId = productId;
	}
			
	
	

}
