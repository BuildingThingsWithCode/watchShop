package com.watchShop.controller;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.service.UserRegistrationService;
import com.watchShop.service.MyUserDetailsService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

	private final MyUserDetailsService myUserDetailsService;
	private final UserRegistrationService userRegistrationService;
	private final AuthenticationManager authenticationManager;

	@GetMapping("/register")
	public String register() {
		return "register";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@PostMapping("/register")
	public String createAndSaveUser(@RequestParam("username") String username, 
			@RequestParam("password") String password, 
			@RequestParam("email") String email) {
		userRegistrationService.registerUser(username, password, email);
		authenticateUser(username, password);
		return "redirect:/";
	}

	private void authenticateUser(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}


}
