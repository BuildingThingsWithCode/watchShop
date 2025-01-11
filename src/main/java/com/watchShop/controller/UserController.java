package com.watchShop.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String showLoginPage(Model model, Form form) {
		return "login";
	}

	@PostMapping("/login")
	public String loginUser(@Valid Form form, BindingResult result, Model model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (result.hasErrors()) {
			model.addAttribute("form", form); 
			return "login"; 
		}
		else authenticateUser(form.getUsername(), form.getPassword());
		return "redirect:/";
	}
	
	private void authenticateUser(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		System.out.println("Authenticate user should have happened");
	}
}
