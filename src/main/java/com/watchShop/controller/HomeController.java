package com.watchShop.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.model.Watch;
import com.watchShop.service.ImageService;
import com.watchShop.service.WatchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final WatchService watchService;	

	@GetMapping("/")
	public String home(@RequestParam(name = "sortOrder", defaultValue = "brand") String sortOrder, Model model) {
		List<Watch> watches = getSortedWatches(sortOrder);
		model.addAttribute("watches", watches);
		model.addAttribute("sortOrder", sortOrder);
		return "home";  
	}

	private List<Watch> getSortedWatches(String sortOrder) {
		if ("high-to-low".equals(sortOrder)) {
			return watchService.findAllByOrderByPriceDesc();
		} 
		if ("low-to-high".equals(sortOrder)) {
			return watchService.findAllByOrderByPriceAsc();
		}
		else return watchService.findAllByOrderByBrand();
	}

}
