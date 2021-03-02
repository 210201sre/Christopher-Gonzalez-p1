package com.revature.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Item;
import com.revature.repositories.ItemDAO;

@Service
public class ItemService {
	
	private static final Logger log = LoggerFactory.getLogger(ItemService.class);
	String event = "event";
	
	@Autowired
	private ItemDAO itemDAO;
	
	public List<Item> getItems(){
		MDC.put(event, "Get Items");
		log.info("Starting == Retrieving items");
		return itemDAO.findAll();
	}
}
