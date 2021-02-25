package com.revature.errorhandling;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiError {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	
	private int status;
	
	private String error;
	
	private String message;
	private String debugMessage;
	
	private ApiError() {
		super();
		timestamp = LocalDateTime.now();
	}
	
	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status.value();
		this.error = status.getReasonPhrase();
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}
}
