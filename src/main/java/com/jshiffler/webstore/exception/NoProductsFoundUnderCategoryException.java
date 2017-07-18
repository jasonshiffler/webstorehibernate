package com.jshiffler.webstore.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// This is the error we'll return if this exception is thrown
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="No products found under this category")
public class NoProductsFoundUnderCategoryException extends RuntimeException {

	private static final long serialVersionUID = 89893488903490034L;
	
	
	
}
