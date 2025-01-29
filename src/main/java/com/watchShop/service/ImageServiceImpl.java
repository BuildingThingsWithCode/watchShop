package com.watchShop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.watchShop.exception.DatabaseException;
import com.watchShop.exception.ImageNotFoundException;
import com.watchShop.model.Image;
import com.watchShop.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

	private final ImageRepository imageRepository;

	public Image saveImage(String pathToImage) {
		Image image = new Image();
		image.setPathToImage(pathToImage);
		try {
			return imageRepository.save(image); 
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while saving image with path :" + pathToImage, e);
		}
	}

	public List<Image> getAllImages() {
		try {
			return imageRepository.findAll();
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while retrieving all images.", e);
		}
	}


	public Image findById(Long id){
		return imageRepository.findById(id)
				.orElseThrow(() -> new ImageNotFoundException("Image not found for ID: " + id));
	}
}
