package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.LoginUserFailedException;
import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.User;
import com.revature.repositories.UserDAO;


@Service
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	String event = "event";
	String uId = "userId";
	
	@Autowired
	private UserDAO userDAO;
	
	public void register(User u) {
		MDC.put(event, "Register");
		log.info("Starting == registering a new user");
		Optional<User> isRegistered = userDAO.findByEmailAndPassword(u.getEmail(),u.getPassword());
		
		if (isRegistered.isPresent()) {
			throw new RegisterUserFailedException();
		}
		
		else {
			userDAO.insertToUsers(u.getEmail(),u.getPassword());
		}
	}
	
	
	public Optional<User> login(User u) {
		MDC.put(event, "Login");
		log.info("Starting == Logging in user");
		Optional<User> isLoggedIn = userDAO.findByEmailAndPassword(u.getEmail(), u.getPassword());
		
		if (!isLoggedIn.isPresent()) {
			throw new LoginUserFailedException();
		}
		
		MDC.put(uId, Integer.toString(isLoggedIn.get().getId()));
		return isLoggedIn;
	}
	
	public List<User> getAllUsers(){
		MDC.put(event, "Retrieve all users");
		log.info("Starting == Looking for users");
		
		return userDAO.findAll();
	}

}
