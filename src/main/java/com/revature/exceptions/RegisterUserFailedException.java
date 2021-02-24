package com.revature.exceptions;

public class RegisterUserFailedException extends Exception{

	public RegisterUserFailedException() {
	}
	
	public RegisterUserFailedException(String message) {
		super(message);
	}
}
