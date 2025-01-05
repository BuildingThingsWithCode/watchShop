package com.watchShop.service;

import java.util.List;
import java.util.Optional;

import com.watchShop.model.Image;

public interface ImageService {
	Image saveImage(String pathToImage);//done
	void saveImagesFromFolder(String folderPath);//done
	List<Image> getAllImages();//done
	Image findById(Long id);//done
}
