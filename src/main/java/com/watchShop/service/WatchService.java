package com.watchShop.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.watchShop.model.Watch;
import com.watchShop.repository.WatchRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class WatchService {
	
	 private final WatchRepository watchRepository;

	    public Watch saveWatch(Watch watch) {
	         return watchRepository.save(watch);
	    }

	
	public Optional<Watch> getWatchById(Long id) {
		return watchRepository.findById(id);
	}
}


