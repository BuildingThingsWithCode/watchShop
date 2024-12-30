package com.watchShop.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.watchShop.model.Image;
import com.watchShop.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image saveImage(String description, String pathToImage) {
        Image image = new Image();
        image.setDescription(description);
        image.setPathToImage(pathToImage);
        return imageRepository.save(image); 
    }
    

    public void saveImagesFromFolder(String folderPath) {
        File folder = new File(folderPath);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".jpg")); 

        if (files != null) {
            for (File file : files) {
                Image image = new Image();
                image.setDescription(file.getName()); 
                image.setPathToImage(file.getAbsolutePath());
                imageRepository.save(image);
            }
        }
    }
    
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
    
    public void rename() {
        List<Image> images = imageRepository.findAll();
        for (Image image : images) {
        	String description = image.getDescription();
           	int l = description.indexOf('.');
        	description = description.substring(0, l);
        	image.setDescription(description);
        	imageRepository.save(image);  
        }
    }
}
