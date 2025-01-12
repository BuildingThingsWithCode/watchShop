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
	public String getCart(Model model, HttpServletResponse response) {
		model.addAttribute("cartItems", cartService.getAll());
		model.addAttribute("totalPrice", cartService.getTotal());
		return "cart"; 
	}

	@PostMapping("/addToCart")
	public String addToCart(@RequestParam("watchId") Long watchId, Model model, HttpServletResponse response) {
		cartService.add(watchService.getWatchById(watchId));
		return "redirect:/cart";
	}

	@PostMapping("/delete-from-cart")
	public String deleteFromCart(@RequestParam(value = "deleteItems", required = false) Optional<List<Long>> deleteItemIds, Model model, HttpServletResponse response) {
		deleteItemIds.ifPresent(v -> cartService.removeItems(v));
		return "redirect:/cart";
	}
}