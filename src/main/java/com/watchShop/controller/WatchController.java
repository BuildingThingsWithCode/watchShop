package com.watchShop.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.dto.WatchDTO;
import com.watchShop.model.Image;
import com.watchShop.model.Watch;
import com.watchShop.service.ImageService;
import com.watchShop.service.WatchService;

@Controller
public class WatchController {
	
	@Autowired
    private WatchService watchService;
	@Autowired
	private ImageService imageService;

	@GetMapping("/watch")
	public String getProductPage(@RequestParam("id") Long id, Model model) {
	    Optional<Watch> watch = watchService.getWatchById(id); 
	    if (watch.isPresent()) {
	    	model.addAttribute("watch", watch.get());
	        return "watch";
	    }
	    else return "redirect:/error.html";

	}
	

	@GetMapping("/add-watch")
	public String showAddWatchForm(Model model) {
	    List<Image> images = imageService.getAllImages(); 
	    model.addAttribute("images", images);
	    model.addAttribute("watchDTO", new WatchDTO());
	    return "add-watch";
	}

    @PostMapping("/add-watch")
    public String addWatch(@ModelAttribute WatchDTO watchDTO, Model model) {
    	System.out.println("yep !");
    	Optional<Image> imageOptional = imageService.findById(watchDTO.getImageId());
        if (!imageOptional.isPresent()) {
            throw new RuntimeException("Image not found");
        }
        Image image = imageOptional.get();
        Watch watch = new Watch();
        watch.setName(watchDTO.getName());
        watch.setBrand(watchDTO.getBrand());
        watch.setPrice(watchDTO.getPrice());
        watch.setDescription(watchDTO.getDescription());
        watch.setImage(image);  
        watchService.saveWatch(watch);
        model.addAttribute("watch", watch);
        return "redirect:/add-watch";
    }
	
}
