package com.watchShop.controller;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.exception.WatchNotInStockException;
import com.watchShop.model.CartItem;
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
	    	Set<Entry<Watch, Integer>> updatedCart = cartService.getAll(); 
	        BigDecimal totalPrice = cartService.getTotal();
	        model.addAttribute("cartItems", updatedCart);
	        model.addAttribute("totalPrice", totalPrice);
	        return "cart"; // This should correspond to your cart.html view
	    }
	   
	    @GetMapping("/addToCart")
	    public String addToCart(@RequestParam("watchId") Long watchId, HttpSession session) {
	        // Check if the cart is empty
	        List<CartItem> cartItems = (List<CartItem>) session.getAttribute("cartItems");

	        // If the cart is empty, redirect to the home page
	        if (cartItems == null || cartItems.isEmpty()) {
	            return "redirect:/";  // Redirect to home page if the cart is empty
	        }

	        // Otherwise, proceed with adding the item to the cart
	        cartService.add(watchService.getWatchById(watchId)); // Your service logic to add item to the cart

	        // Redirect back to the cart page (or another page if desired)
	        return "redirect:/cart";  // Redirect to cart page after adding the item
	    }
	    
	    @GetMapping("/delete-from-cart")
	    public String redirecHome2(Model model) throws WatchNotInStockException {
	    	return "redirect:/"; 
	    }
	    
	    @PostMapping("/addToCart")
	    public String addToCart(@RequestParam("watchId") Long watchId, Model model) throws WatchNotInStockException {
	        cartService.add(watchService.getWatchById(watchId));
	        boolean isCartEmpty = cartService.isEmpty();
	        model.addAttribute("isCartEmpty", isCartEmpty);
	        model.addAttribute("cartItems", cartService.getAll());
	        model.addAttribute("totalPrice", cartService.getTotal());
	        return "cart.html";
	    }
	    
	    @PostMapping("/delete-from-cart")
	    public String deleteFromCart(@RequestParam("deleteItems") List<Long> deleteItemIds, Model model) throws WatchNotInStockException {
	        cartService.removeItems(deleteItemIds);
	        Set<Entry<Watch, Integer>> updatedCart = cartService.getAll(); 
	        BigDecimal totalPrice = cartService.getTotal();
	        boolean isCartEmpty = cartService.isEmpty();
	        model.addAttribute("isCartEmpty", isCartEmpty);
	        model.addAttribute("cartItems", updatedCart);
	        model.addAttribute("totalPrice", totalPrice);
	        return "cart"; 
	    }
	}