package com.watchShop.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.watchShop.dto.WatchDTO;
import com.watchShop.model.Watch;

public interface WatchService {
	Watch saveWatch(WatchDTO watchDT);
	Watch getWatchById(Long id);
	ResponseEntity<String> getWatchDescription(@PathVariable Long id);
	ResponseEntity<HttpStatus> deleteWatch(@PathVariable Long id);
	ResponseEntity<String> getWatchInfo(@PathVariable Long id);
	Watch getWatchByImage(@PathVariable Long id);
	List<Watch> getAllWatches();
	List<Watch> findAllByOrderByPriceDesc();
	List<Watch> findAllByOrderByPriceAsc();
	List<Watch> findAllByOrderByBrand();
}
