package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.exceptions.LoginUserFailedException;
import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.Item;
import com.revature.models.User;
import com.revature.services.CartService;
import com.revature.services.ItemService;
import com.revature.services.UserService;

@RestController
public class UserController {

	private static final Logger log = LoggerFactory.getLogger(UserController.class);
	String end = "Ending ==";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartService cartService;
	
	@Autowired
	private ItemService itemService;
	
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody User u){
		
		try {
			userService.register(u);
		} catch (RegisterUserFailedException e) {
			e.printStackTrace();
			return new ResponseEntity<>("User is already registered.",HttpStatus.CONFLICT); 
		}
		
		MDC.clear();
		return new ResponseEntity<>("Registered successfully!",HttpStatus.CREATED);
	}
	
	@PostMapping("login")
	public ResponseEntity<String> login(@Valid @RequestBody User u, HttpServletResponse response){
		
		try {
			Optional<User> loggedInUser = userService.login(u);
			loggedInUser.ifPresent(user -> {
				Cookie cookie = new Cookie("my-key",Integer.toString(user.getId()));
				cookie.setMaxAge(1 * 24 * 60 * 60);
				response.addCookie(cookie);
			});
		} catch (LoginUserFailedException e) {
			e.printStackTrace();
			return new ResponseEntity<>("Email and/or Password not correct.",HttpStatus.BAD_REQUEST); 
		}
		
		
		
		MDC.clear();
		return new ResponseEntity<>("Logged in successfully!",HttpStatus.OK);
	}
	
	@GetMapping("/items")
	public ResponseEntity<List<Item>> getItems(){
		
		List<Item> items = itemService.getItems();
		
		if (items.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		log.info(end);
		MDC.clear();
		return ResponseEntity.ok(items);
	}
	
	@PostMapping("items/{id}")
	public ResponseEntity<String> addItemToCart(@PathVariable("id") String id, HttpServletRequest request){
		
		
		Cookie cookies;
		try{
			cookies = request.getCookies()[0];
		}
		catch (Exception e) {
			return new ResponseEntity<>("You need to login.",HttpStatus.BAD_REQUEST);
		}
		
		String cookie = cookies.getValue();
		int userId = Integer.parseInt(cookie);
		
		int itemId = Integer.parseInt(id);
		
		cartService.addItemToCart(itemId,userId);
		log.info(end);
		MDC.clear();
        return new ResponseEntity<>("Item was successfully added to cart!",HttpStatus.CREATED);
	}
	
	@PostMapping("order")
	public ResponseEntity<String> addItemsToOrder(HttpServletRequest request){
		Cookie cookies;
		try{
			cookies = request.getCookies()[0];
		}
		catch (Exception e) {
			return new ResponseEntity<>("You need to login.",HttpStatus.BAD_REQUEST);
		}
		
		String cookie = cookies.getValue();
		int userId = Integer.parseInt(cookie);
		
		cartService.addItemToOrder(userId);
		MDC.clear();
		return new ResponseEntity<>("Cart was successfully ordered!",HttpStatus.CREATED);
	}
	@PostMapping("logout")
	public ResponseEntity<String> logout(HttpServletResponse response, HttpServletRequest request){
		Cookie cookies;
		try{
			cookies = request.getCookies()[0];
		}
		catch (Exception e) {
			return new ResponseEntity<>("You need to login.",HttpStatus.BAD_REQUEST);
		}
		String cookie = cookies.getValue();
		int userId = Integer.parseInt(cookie);
		
		MDC.put("userId", Integer.toString(userId));
		MDC.put("event", "Logout");
		log.info("Logged out");
		
		Cookie newCookie = new Cookie("my-key", null);
		newCookie.setMaxAge(0);
		response.addCookie(newCookie);
		MDC.clear();
		return new ResponseEntity<>("Logged out successfully!",HttpStatus.OK);
	}
	
	@GetMapping("users")
	public ResponseEntity<List<User>> getUsers(){
		List<User> users = userService.getAllUsers();
		
		if (users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		log.info("Ending == successfully retrieved users!");
		MDC.clear();
		return ResponseEntity.ok(users);
	}
}
