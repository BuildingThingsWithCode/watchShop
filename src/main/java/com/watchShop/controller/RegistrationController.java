package com.watchShop.controller;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.watchShop.model.AppUser;
import com.watchShop.service.AppUserServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

	private final AppUserServiceImpl appUserService;
	
	@GetMapping("/register")
	public String register() {
		return "register";
	}
	
	@PostMapping("/register")
	public AppUser createAndSaveUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email) {
		return appUserService.saveUser(username, password, email);
	}
	
	
}
