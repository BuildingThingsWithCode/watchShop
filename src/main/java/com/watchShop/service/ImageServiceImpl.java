package com.watchShop.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.watchShop.exception.DatabaseException;
import com.watchShop.exception.ImageNotFoundException;
import com.watchShop.exception.IncorrectFilePathException;
import com.watchShop.exception.WatchNotFoundException;
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


	public void saveImagesFromFolder(String filePath) {
		File folder = new File(filePath);
		if (!folder.exists() || !folder.isDirectory()) {
			throw new IncorrectFilePathException("File with path : " + filePath + " could not be found");
		}
		File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg")); 
		if (files != null) {
			for (File file : files) {
				Image image = new Image();
				image.setPathToImage(file.getAbsolutePath());
				try {
					imageRepository.save(image);
				} catch (DatabaseException e) {
					throw new DatabaseException("Database error occurred while saving image with path :" + file.getAbsolutePath(), e);
				}
			}
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
		try {
			return imageRepository.findById(id)
					.orElseThrow(() -> new ImageNotFoundException("Image not found for ID: " + id));
		} catch (DatabaseException e) {
			throw new DatabaseException("Database error occurred while retrieving image with ID: " + id, e);
		}
	}
}
