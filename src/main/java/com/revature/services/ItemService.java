package com.revature.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Item;
import com.revature.repositories.ItemDAO;

@Service
public class ItemService {
	
	@Autowired
	private ItemDAO itemDAO;
	
	public List<Item> getItems(){
		
		return itemDAO.findAll();
	}
}
