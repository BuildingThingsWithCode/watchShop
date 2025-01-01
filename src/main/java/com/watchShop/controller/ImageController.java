package com.watchShop.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.watchShop.service.ImageService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/img")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/import")
    public String importImages(@RequestParam String folderPath) {
        imageService.saveImagesFromFolder(folderPath);
        return "Images imported successfully!";
    }
 }
