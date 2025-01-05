package com.watchShop.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.model.Watch;
import com.watchShop.service.CartService;
import com.watchShop.service.WatchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final WatchService watchService;	
	private final CartService cartService;

	@GetMapping("/")
	public String home(@RequestParam(name = "sortOrder", defaultValue = "brand") String sortOrder, Model model, HttpServletResponse response) {
		boolean isCartEmpty = cartService.isEmpty();
		List<Watch> watches = watchService.findAllByOrder(sortOrder);
		model.addAttribute("isCartEmpty", isCartEmpty);
		model.addAttribute("watches", watches);
		model.addAttribute("sortOrder", sortOrder);
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		return "home";  
	}
}
