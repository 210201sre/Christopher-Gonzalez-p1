package com.revature.advice;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.revature.errorhandling.ApiError;
import com.revature.exceptions.LoginException;
import com.revature.exceptions.LoginUserFailedException;
import com.revature.exceptions.RegisterUserFailedException;
import com.revature.exceptions.CartEmptyException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler{
	
	private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);
	String event = "event";
	
	public ResponseEntity<Object> buildResponseEntity(ApiError apiError){
		return ResponseEntity.status(apiError.getStatus()).body(apiError);
	}
	
	@ExceptionHandler(RegisterUserFailedException.class)
	public ResponseEntity<Object> handleRegisterException(RegisterUserFailedException ex){
		
		MDC.put(event, "register error");
		log.error("User is already registered.", ex);
		
		String error = "User is already registered.";
		return buildResponseEntity(new ApiError(HttpStatus.CONFLICT, error, ex));
	}
	
	@ExceptionHandler(LoginUserFailedException.class)
	public ResponseEntity<Object> handleLoginException(LoginUserFailedException ex){
		
		MDC.put(event, "login error");
		log.error("Email and/or Password not correct.", ex);
		
		String error = "Email and/or Password not correct.";
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, ex));
	}
	
	@ExceptionHandler(LoginException.class)
	public ResponseEntity<Object> handleNotLoginException(LoginException ex){
		
		MDC.put(event, "access error");
		log.error("You need to login.", ex);
		
		String error = "You need to login.";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, ex));
	}
	
	@ExceptionHandler(CartEmptyException.class)
	public ResponseEntity<Object> handleEmptyCartException(CartEmptyException ex){
		
		MDC.put(event, "cart empty error");
		log.error("There are no items in your cart.", ex);
		
		String error = "There are no items in your cart.";
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error ,ex));
	}
}
