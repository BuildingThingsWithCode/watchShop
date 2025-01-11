package com.watchShop.controller;

import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.watchShop.model.Form;
import com.watchShop.service.MyUserDetailsService;
import com.watchShop.service.UserRegistrationService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final MyUserDetailsService myUserDetailsService;
	private final UserRegistrationService userRegistrationService;
	private final AuthenticationManager authenticationManager;

	@GetMapping("/register")
	public String showRegisterPage() {
		return "register";
	}


	@PostMapping("/register")
	public String createAndSaveUser(@RequestParam("username") String username, 
			@RequestParam("password") String password, 
			@RequestParam("email") String email) {
		userRegistrationService.registerUser(username, password, email);
		authenticateUser(username, password);
		return "redirect:/";
	}

	@GetMapping("/login")
	public String showLoginPage(Model model) {
		model.addAttribute("form", new Form());
		System.out.println("login is called");
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@Valid Form form, BindingResult result, Model model) {
		if (result.hasErrors()) {
			model.addAttribute("form", form); 
			return "login"; 
		}
		return "redirect:/"; 
	}

	@GetMapping("/login-auth")
	public String loginAuth(@ModelAttribute("username") String username, 
	                       @ModelAttribute("password") String password) {
	    return "login-auth";
	}

	private void authenticateUser(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("Authenticate user should have happened");
	}


}
