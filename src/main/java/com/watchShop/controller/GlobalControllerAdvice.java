package com.watchShop.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.watchShop.service.CartService;

@ControllerAdvice
public class GlobalControllerAdvice {

	@Autowired
	private CartService cartService;

	@ModelAttribute
	public void addCartStatus(Model model) {
		model.addAttribute("isCartEmpty", cartService.isEmpty());
	}
	
	 @ModelAttribute
	    public void setCacheHeaders(HttpServletResponse response) {
	        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
	        response.setHeader("Pragma", "no-cache");
	        response.setDateHeader("Expires", 0);
	    }
	 
	 @ExceptionHandler(Exception.class)
	    public String handleException(Exception ex, Model model) {
		 System.out.println("Exception is being called " + ex.getMessage());
	        model.addAttribute("isCartEmpty", cartService.isEmpty());
	        model.addAttribute("errorMessage", ex.getMessage());
	        return "error";  
	    }
}