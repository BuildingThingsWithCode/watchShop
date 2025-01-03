package com.watchShop.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.watchShop.dto.WatchDTO;
import com.watchShop.model.Image;
import com.watchShop.model.Watch;
import com.watchShop.service.CartService;
import com.watchShop.service.ImageService;
import com.watchShop.service.WatchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/watch")
@RequiredArgsConstructor
public class WatchController {

	final private WatchService watchService;
	final private ImageService imageService;
    private final CartService cartService;

	
	@GetMapping("/{id}")
	public String getInfoPage(@PathVariable("id") Long id, Model model) {
		Watch watch = watchService.getWatchById(id);
		boolean isCartEmpty = cartService.isEmpty();
        model.addAttribute("isCartEmpty", isCartEmpty);
		model.addAttribute("watch", watch);
		model.addAttribute("image", "/" + watch.getImage().getPathToImage());
		return "info";
	}
	
	@GetMapping("/add")
	public String showAddWatchForm(Model model) {
		boolean isCartEmpty = cartService.isEmpty();
        model.addAttribute("isCartEmpty", isCartEmpty);
		List<Image> images = imageService.getAllImages(); 
		model.addAttribute("images", images);
		model.addAttribute("watchDTO", new WatchDTO());
		return "add";
	}

	@PostMapping("/add")
	public String addWatch(@ModelAttribute WatchDTO watchDTO, Model model) {
		boolean isCartEmpty = cartService.isEmpty();
        model.addAttribute("isCartEmpty", isCartEmpty);
		watchService.saveWatch(watchDTO);
		return "redirect:/watch/add";
	}
	
	@GetMapping("/description/{id}")
	public ResponseEntity<String> getWatchDescription(@PathVariable Long id) {
		Watch watch = watchService.getWatchById(id);
		return ResponseEntity.ok(watch.getDescription()); 
	}


	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteWatch(@PathVariable Long id) {
		return watchService.deleteWatch(id);
	}
	
//	@GetMapping("/info/{id}")
//	public ResponseEntity<String> getWatchInfo(@PathVariable Long id) {
//		return watchService.getWatchInfo(id);
//	}
	
}
