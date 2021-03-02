package com.revature.exceptions;

public class CartEmptyException extends RuntimeException {

	public CartEmptyException() {
	}
	
	public CartEmptyException(String message) {
		super(message);
	}
}
