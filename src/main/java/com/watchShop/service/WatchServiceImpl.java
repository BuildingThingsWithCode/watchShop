package com.watchShop.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.watchShop.dto.WatchDTO;
import com.watchShop.dto.WatchMapper;
import com.watchShop.exception.DatabaseException;
import com.watchShop.exception.WatchNotFoundException;
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
		Image image = imageService.findById(watchDTO.getImageId());
		Watch watch = watchMapper.toEntity(watchDTO, image);
		try {
		return watchRepository.save(watch);
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while saving watch with ID: " + watch.getId(), e);
		}
	}

	public Watch getWatchByImage(Long imageId) {
		try {
			return watchRepository.findByImageId(imageId)
					.orElseThrow(() -> new WatchNotFoundException("Watch not found for image id: " + imageId));
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while retrieving image with ID: " + imageId, e);
		}
	}

	public Watch getWatchById(Long id) {
		try {
			return watchRepository.findById(id)
					.orElseThrow(() -> new WatchNotFoundException("Watch not found for ID: " + id));
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while retrieving watch with ID: " + id, e);
		}

	}



	public void deleteWatch(Long id) {
		try {
			watchRepository.deleteById(id);
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while deleting watch with ID: " + id, e);
		}
	}


	@Override
	public String getWatchDescription(Long id) {
		try {
			return watchRepository.findById(id)
					.orElseThrow(() -> new WatchNotFoundException("Watch not found for ID: " + id))
					.getDescription();
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while retrieving watch with ID: " + id, e);
		}
	}


	@Override
	public String getWatchInfo(Long id) {
		try {
			Watch watch = watchRepository.findById(id)
					.orElseThrow(() -> new WatchNotFoundException("Watch not found for ID: " + id));
			return watch.getId() + " " + watch.getName() + " " + watch.getBrand() + " " + 
			watch.getPrice() + " " +watch.getImage().getId();
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while retrieving watch with ID: " + id, e);
		}
	}

	@Override
	public List<Watch> getAllWatches() {
		try {
			return watchRepository.findAll();
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while retrieving all watches.", e);
		}
	}

	@Override
	public List<Watch> findAllByOrder(String sortOrder) {
		try {
			if ("high-to-low".equals(sortOrder)) {
				return watchRepository.findAll(Sort.by(Sort.Order.desc("price")));
			} 
			if ("low-to-high".equals(sortOrder)) {
				return watchRepository.findAll(Sort.by(Sort.Order.asc("price")));
			}
			else return watchRepository.findAll(Sort.by(Sort.Order.asc("brand")));
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while retrieving all watches.", e);
		}
	}
}


