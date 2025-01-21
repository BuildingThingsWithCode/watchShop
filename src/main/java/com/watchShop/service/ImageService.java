package com.watchShop.service;

import java.util.List;

import com.watchShop.model.Image;

public interface ImageService {
	Image saveImage(String pathToImage);
	List<Image> getAllImages();
	Image findById(Long id);
}
