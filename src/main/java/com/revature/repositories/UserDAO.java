package com.revature.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.models.User;


public interface UserDAO extends JpaRepository<User, Integer>{
	
	public Optional <User> findByEmailAndPassword(String email, String password);
	
	@Transactional
	@Modifying 
	@Query(value = "INSERT INTO projectzero.users (email,password) VALUES (:email,:password)",
			  nativeQuery = true)
	public void insertToUsers(@Param("email") String email, @Param("password") String password);
	
	
}
