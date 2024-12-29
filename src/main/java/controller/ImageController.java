package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import service.ImageService;

@RestController
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/importImages")
    public String importImages(@RequestParam String folderPath) {
        imageService.saveImagesFromFolder(folderPath);
        return "Images imported successfully!";
    }
    
    @PostMapping("/rename")
    public void rename() {
    	imageService.rename();;
    }
}
