package com.watchShop.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.model.Watch;
import com.watchShop.service.WatchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final WatchService watchService;


	@GetMapping("/")
	public String home(@RequestParam(name = "sortOrder", defaultValue = "brand") String sortOrder, Model model, HttpServletResponse response) {
		List<Watch> watches = watchService.findAllByOrder(sortOrder);
		model.addAttribute("watches", watches);
		model.addAttribute("sortOrder", sortOrder);
		return "home";  
	}
	
	@GetMapping("/test")
	public String test() {
		return "base";
	}
}	

