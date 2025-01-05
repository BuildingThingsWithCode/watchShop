package com.watchShop.controller;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.model.Watch;
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
		doChecksAndSetHeader(model, response);
		return "cart"; 
	}
	
	@PostMapping("/addToCart")
	public String addToCart(@RequestParam("watchId") Long watchId, Model model, HttpServletResponse response) {
		cartService.add(watchService.getWatchById(watchId));
		doChecksAndSetHeader(model, response);
		return "redirect:/cart";
	}

	@PostMapping("/delete-from-cart")
	public String deleteFromCart(@RequestParam(value = "deleteItems", required = false) List<Long> deleteItemIds, Model model, HttpServletResponse response) {
		if (deleteItemIds == null || deleteItemIds.isEmpty()) {
			return "redirect:/cart";
		}
		cartService.removeItems(deleteItemIds);
		doChecksAndSetHeader(model, response);
		return "redirect:/cart";
	}
	
	// Help method do the necessary checks and to set the response header.
	private Model doChecksAndSetHeader(Model model, HttpServletResponse response) {
		Set<Entry<Watch, Integer>> updatedCart = cartService.getAll(); 
		//int rowCount = updatedCart.size();
		BigDecimal totalPrice = cartService.getTotal();
		boolean isCartEmpty = cartService.isEmpty();
		model.addAttribute("isCartEmpty", isCartEmpty);
		//model.addAttribute("rowCount", rowCount);
		model.addAttribute("cartItems", updatedCart);
		model.addAttribute("totalPrice", totalPrice);
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		return model;
	}
}