package com.revature.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.models.Order;


public interface OrderDAO extends JpaRepository<Order, Integer>{
	
	@Transactional
	@Modifying 
	@Query(value = "INSERT INTO projectzero.orders (item_id,user_id) VALUES (:itemId,:userId)",
			  nativeQuery = true)
	public void insertToOrders(@Param("itemId") int itemId, @Param("userId") int userId);
}
