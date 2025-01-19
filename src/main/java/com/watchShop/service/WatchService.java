package com.watchShop.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.watchShop.dto.WatchDto;
import com.watchShop.model.Watch;

public interface WatchService {
	Watch saveWatch(WatchDto watchDT); 
	Watch getWatchById(Long id); 
	ResponseEntity<String> getWatchDescription(@PathVariable Long id);
	void deleteWatch(@PathVariable Long id); 
	ResponseEntity<String> getWatchInfo(@PathVariable Long id); 
	Watch getWatchByImage(@PathVariable Long id); 
	List<Watch> getAllWatches();
	List<Watch> findAllByOrder(String sortOrder);
}
