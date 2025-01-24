package com.watchShop.controller;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.service.CartService;
import com.watchShop.service.WatchService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CartController {

	private final CartService cartService;
	private final WatchService watchService;

	@GetMapping("/cart")
	public String getCart(Model model) {
		model.addAttribute("cartItems", cartService.getAll());
		model.addAttribute("totalPrice", cartService.getTotal());
		return "cart"; 
	}

	@PostMapping("/add-to-cart")
	public String addToCart(@RequestParam("watchId") Long watchId) {
		cartService.add(watchService.getWatchById(watchId));
		return "redirect:/cart";
	}

	@PostMapping("/delete-from-cart")
	public String deleteFromCart(@RequestParam(value = "deleteItems", required = false) List<Long> deleteItemIds) {
		if (deleteItemIds != null && !deleteItemIds.isEmpty()) {
	        cartService.removeItems(deleteItemIds);
	    }
		return "redirect:/cart";
	}
}