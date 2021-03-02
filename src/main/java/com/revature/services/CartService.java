package com.revature.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.CartEmptyException;
import com.revature.models.Cart;
import com.revature.repositories.CartDAO;
import com.revature.repositories.OrderDAO;

@Service
public class CartService {

	private static final Logger log = LoggerFactory.getLogger(CartService.class);
	String event = "event";
	String uId = "userId";
	
	@Autowired
	private CartDAO cartDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	public void addItemToCart(int itemId, int userId) {
		MDC.put(event, "Add item to cart");
		log.info("Starting == Adding item " + itemId + " to cart");
		cartDAO.insertToCarts(itemId, userId);
	}
	
	public void addItemToOrder(int userId) {
		MDC.put(event, "Add Items to Order");
		MDC.put(uId, Integer.toString(userId));
		log.info("Starting == Adding item to Order");
		
		List<Cart> userCart = cartDAO.findByUserId(userId);
		if (userCart.isEmpty()) {
			throw new CartEmptyException();
		}
		for (Cart item : userCart) {
			cartDAO.deleteById(item.getId());
			orderDAO.insertToOrders(item.getItem_id(), userId);
			
			log.info("user# " + userId + " ordered item# " + item.getId());
		}
		userCart.clear();
	}
}
