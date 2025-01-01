package com.watchShop.service;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.watchShop.dto.WatchDTO;
import com.watchShop.dto.WatchMapper;
import com.watchShop.model.Image;
import com.watchShop.model.Watch;
import com.watchShop.repository.WatchRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WatchServiceImpl implements WatchService{

	private final WatchRepository watchRepository;
	private final ImageService imageService;
	private final WatchMapper watchMapper;

	public Watch saveWatch(WatchDTO watchDTO) {
		Optional<Image> optionalImage = imageService.findById(watchDTO.getImageId());
		if (optionalImage.isPresent()) {
			Watch watch = watchMapper.toEntity(watchDTO, optionalImage.get());
			return watchRepository.save(watch);
		}
		else throw new RuntimeException("Image not found");
	}
	
	public Watch getWatchByImage(Long imageId) {
	    return watchRepository.findByImageId(imageId)
	            .orElseThrow(() -> new EntityNotFoundException("Watch not found for image id: " + imageId));
	}

	public Watch getWatchById(Long id) {
		Optional<Watch> watch = watchRepository.findById(id); 
		if (watch.isPresent()) return watch.get();
		else throw new RuntimeException("Watch not found");
	}
	
	
	
	public ResponseEntity<HttpStatus> deleteWatch(Long id) {
		try {
			watchRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}


	@Override
	public ResponseEntity<String> getWatchDescription(Long id) {
		Optional<Watch> watchOpt = watchRepository.findById(id);
		if (watchOpt.isPresent()) {
			return ResponseEntity.status(HttpStatus.OK).body(watchOpt.get().getDescription()); 
		}
		else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Watch not found");
		
	}


	@Override
	public ResponseEntity<String> getWatchInfo(Long id) {
		Optional<Watch> watchOpt = watchRepository.findById(id);
		if (watchOpt.isPresent()) {
			Watch watch = watchOpt.get();
			return ResponseEntity.status(HttpStatus.OK).body(watch.getId() + " " + watch.getName() + " " + watch.getBrand() + " " + 
			watch.getPrice() + " " +watch.getImage().getId());
		}
		else return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Watch not found");
	}

}


