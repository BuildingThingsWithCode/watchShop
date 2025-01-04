package com.watchShop.controller;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.watchShop.exception.WatchNotInStockException;
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
	public String getCart(Model model) throws WatchNotInStockException {
		modelChecks(model);
		return "cart"; 
	}

	@GetMapping("/delete-from-cart")
	public String redirecHome2(Model model) throws WatchNotInStockException {

		if (model.getAttribute("cartItems") == null || model.getAttribute("cartItems")=="") {
			// Optionally add an error message if no items were selected for deletion
			return "/cart";
		}
		modelChecks(model);
		return "redirect:/cart"; 
	}

	@PostMapping("/addToCart")
	public String addToCart(@RequestParam("watchId") Long watchId, Model model, HttpServletResponse response) throws WatchNotInStockException {
		cartService.add(watchService.getWatchById(watchId));
		modelChecks(model);
		setResponseHeader(response);
		return "redirect:/cart";
	}

	@PostMapping("/delete-from-cart")
	public String deleteFromCart(@RequestParam(value = "deleteItems", required = false) List<Long> deleteItemIds, Model model, HttpServletResponse response) throws WatchNotInStockException {
		if (deleteItemIds == null || deleteItemIds.isEmpty()) {
			return "redirect:/cart";
		}
		cartService.removeItems(deleteItemIds);
		modelChecks(model);
		setResponseHeader(response);
		return "redirect:/cart";
	}

	private void setResponseHeader(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
	}

	private Model modelChecks(Model model) throws WatchNotInStockException {
		Set<Entry<Watch, Integer>> updatedCart = cartService.getAll(); 
		int rowCount = updatedCart.size();
		BigDecimal totalPrice = cartService.getTotal();
		boolean isCartEmpty = cartService.isEmpty();
		model.addAttribute("isCartEmpty", isCartEmpty);
		model.addAttribute("rowCount", rowCount);
		model.addAttribute("cartItems", updatedCart);
		model.addAttribute("totalPrice", totalPrice);
		return model;
	}
}