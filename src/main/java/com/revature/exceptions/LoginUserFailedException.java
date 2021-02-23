package com.revature.exceptions;

public class LoginUserFailedException extends Exception{

	public LoginUserFailedException() {
	}
	
	public LoginUserFailedException(String message) {
		super(message);
	}
}
