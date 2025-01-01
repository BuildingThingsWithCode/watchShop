package com.watchShop.service;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.watchShop.model.Image;
import com.watchShop.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    
    public Image saveImage(String description, String pathToImage) {
        Image image = new Image();
        image.setPathToImage(pathToImage);
        return imageRepository.save(image); 
    }
    

    public void saveImagesFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg")); 

        if (files != null) {
            for (File file : files) {
                Image image = new Image();
                image.setPathToImage(file.getAbsolutePath());
                imageRepository.save(image);
            }
        }
    }
    
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
    
   public Optional<Image> findById(Long id){
    	return imageRepository.findById(id);
    }
}
