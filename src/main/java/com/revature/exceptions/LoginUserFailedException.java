package com.revature.exceptions;

public class LoginUserFailedException extends RuntimeException{

	public LoginUserFailedException() {
	}
	
	public LoginUserFailedException(String message) {
		super(message);
	}
}
