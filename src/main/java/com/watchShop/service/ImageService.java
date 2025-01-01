package com.watchShop.service;

import java.util.List;
import java.util.Optional;

import com.watchShop.model.Image;

public interface ImageService {
	Image saveImage(String description, String pathToImage);
	void saveImagesFromFolder(String folderPath);
	List<Image> getAllImages();
	Optional<Image> findById(Long id);
}
