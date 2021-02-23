package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Cart;
import com.revature.repositories.CartDAO;
import com.revature.repositories.OrderDAO;

@Service
public class CartService {

	@Autowired
	private CartDAO cartDAO;
	
	@Autowired
	private OrderDAO orderDAO;
	
	public void addItemToCart(int itemId, int userId) {
		cartDAO.insertToCarts(itemId, userId);
	}
	
	public void addItemToOrder(int userId) {
		
		List<Cart> userCart = cartDAO.findByUserId(userId);
		
		for (Cart item : userCart) {
			cartDAO.deleteById(item.getId());
			orderDAO.insertToOrders(item.getItem_id(), userId);
		}
	}
}
