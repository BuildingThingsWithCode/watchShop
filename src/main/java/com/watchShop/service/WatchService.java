package com.watchShop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.watchShop.dto.WatchDTO;
import com.watchShop.model.Watch;

public interface WatchService {
	Watch saveWatch(WatchDTO watchDT); //done
	Watch getWatchById(Long id); // done
	String getWatchDescription(@PathVariable Long id); // done
	void deleteWatch(@PathVariable Long id); //done
	String getWatchInfo(@PathVariable Long id); //done
	Watch getWatchByImage(@PathVariable Long id); //done
	List<Watch> getAllWatches();//done
	List<Watch> findAllByOrder(String sortOrder);//done
}
