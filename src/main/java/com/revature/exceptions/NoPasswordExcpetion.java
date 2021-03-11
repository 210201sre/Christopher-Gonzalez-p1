package com.revature.exceptions;

public class NoPasswordExcpetion extends RuntimeException {

	public NoPasswordExcpetion() {
	}
	
	public NoPasswordExcpetion(String message) {
		super(message);
	}
}
