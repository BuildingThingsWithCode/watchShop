package com.watchShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watchShop.service.ImageService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {
	
	 private final ImageService imageService;
	
	 /*
	  * Todo:
	  * Get all watches and set Brand, name , price + picture.
	  */
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("images", imageService.getAllImages());
		return "home";
	}

}
