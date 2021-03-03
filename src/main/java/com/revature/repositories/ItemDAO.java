package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.models.Item;


public interface ItemDAO extends JpaRepository<Item, Integer>{

}
