package com.revature.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.models.Cart;


public interface CartDAO extends JpaRepository<Cart, Integer>{

	@Transactional
	@Modifying 
	@Query(value = "INSERT INTO projectzero.carts (item_id,user_id) VALUES (:itemId,:userId)",
			  nativeQuery = true)
	public void insertToCarts(@Param("itemId") int itemId, @Param("userId") int userId);
	
	@Query(value = "SELECT * FROM projectzero.carts WHERE user_id = :userId",
			  nativeQuery = true)
	public List<Cart> findByUserId(@Param("userId") int userId);
	
}
