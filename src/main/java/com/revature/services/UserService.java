package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.LoginUserFailedException;
import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.User;
import com.revature.repositories.UserDAO;


@Service
public class UserService {

	@Autowired
	private UserDAO userDAO;
	
	public void register(User u) throws RegisterUserFailedException {
		Optional<User> isRegistered = userDAO.findByEmailAndPassword(u.getEmail(),u.getPassword());
		
		if (isRegistered.isPresent()) {
			System.out.println(isRegistered);
			throw new RegisterUserFailedException("Failed to register new User");
		}
		
		else {
			userDAO.insertToUsers(u.getEmail(),u.getPassword());
		}
	}
	
	public Optional<User> login(User u) throws LoginUserFailedException {
		
		Optional<User> isLoggedIn = userDAO.findByEmailAndPassword(u.getEmail(), u.getPassword());
		
		if (!isLoggedIn.isPresent()) {
			System.out.println(isLoggedIn);
			throw new LoginUserFailedException("User does not exist");
		}
		return isLoggedIn;
	}
	
	public List<User> getAllUsers(){
		return userDAO.findAll();
	}

}
