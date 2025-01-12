package com.watchShop.controller;

import javax.validation.Valid;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.watchShop.model.Form;
import com.watchShop.model.RegisterForm;
import com.watchShop.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
	@GetMapping("/login")
	public String showLoginPage(Form form, Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = false;
		if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
			isAuthenticated = true;
		}
		model.addAttribute("authentication", isAuthenticated);
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@Valid Form form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("form", form); 
			return "login"; 
		}
		else userService.authenticateUser(form.getUsername(), form.getPassword());
		return "redirect:/";
	}
		
	@GetMapping("/register")
	public String showRegisterPage(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		boolean isAuthenticated = false;
		if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
			isAuthenticated = true;
		}
		model.addAttribute("authentication", isAuthenticated);
	    model.addAttribute("form", new RegisterForm());
	    return "register"; 
	}

	@PostMapping("/register")
	public String createAndSaveUser(@Valid @ModelAttribute("form") RegisterForm form, BindingResult result, Model model) {
	    if (result.hasErrors()) {
	        model.addAttribute("form", form); 
	        return "register";
	    }
		userService.registerUser(form.getUsername(), form.getPassword(), form.getEmail());
		userService.authenticateUser(form.getUsername(), form.getPassword());
		return "redirect:/";
	}
}
