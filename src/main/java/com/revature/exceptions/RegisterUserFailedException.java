package com.revature.exceptions;

public class RegisterUserFailedException extends RuntimeException{

	public RegisterUserFailedException() {
	}
	
	public RegisterUserFailedException(String message) {
		super(message);
	}
}
