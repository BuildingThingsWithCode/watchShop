package com.watchShop.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.watchShop.dto.WatchDto;
import com.watchShop.model.Image;
import com.watchShop.model.Watch;
import com.watchShop.service.ImageService;
import com.watchShop.service.WatchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/watch")
@RequiredArgsConstructor
public class WatchController {

	final private WatchService watchService;
	final private ImageService imageService;

	
	@GetMapping("/{id}")
	public String getInfoPage(@PathVariable("id") Long id, Model model) {
		Watch watch = watchService.getWatchById(id);
		model.addAttribute("watch", watch);
		model.addAttribute("image", "/" + watch.getImage().getPathToImage());
		return "info";
	}
	
	// All methods below this point were only used to populate the database before 
	// I knew how to use psql.
	@GetMapping("/add")
	public String showAddWatchForm(Model model) {
		List<Image> images = imageService.getAllImages();
		model.addAttribute("images", images);
		model.addAttribute("watchDTO", new WatchDto());
		return "add";
	}

	@PostMapping("/add")
	public String addWatch(@ModelAttribute WatchDto watchDTO, Model model) {
		watchService.saveWatch(watchDTO);
		return "redirect:/watch/add";
	}
	
	@GetMapping("/description/{id}")
	public ResponseEntity<String> getWatchDescription(@PathVariable Long id) {
		Watch watch = watchService.getWatchById(id);
		return ResponseEntity.ok(watch.getDescription()); 
	}


	@DeleteMapping("/delete/{id}")
	public void deleteWatch(@PathVariable Long id) {
		 watchService.deleteWatch(id);
	}
	
	@GetMapping("/info/{id}")
	@ResponseBody
	public ResponseEntity<String> getWatchInfo(@PathVariable Long id) {
		return watchService.getWatchInfo(id);
	}
	
}
