package com.watchShop.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.watchShop.model.Role;
import com.watchShop.model.User;
import com.watchShop.repository.RoleRepository;
import com.watchShop.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleRepository roleRepository;
	private final AuthenticationManager authenticationManager;


	public User registerUser(String username, String password, String email) {
		if (userRepository.findByUsername(username).isPresent()) {
			throw new RuntimeException("Username already exists. Please choose another one.");
		}
		if (userRepository.findByEmail(email).isPresent()) {
			throw new RuntimeException("Email already exists. Please choose another one.");
		}
		Set<Role> roles = new HashSet<>();
		Role userRole = roleRepository.findByName("USER")
				.orElseThrow(() -> new RuntimeException("Role USER not found"));
		roles.add(userRole);
		User user = User.builder()
				.username(username)
				.password(passwordEncoder.encode(password))
				.email(email)
				.roles(roles)
				.build();
		return userRepository.save(user);
	}

	public void authenticateUser(String username, String password) {
		if (username == null || password == null) {
			throw new IllegalArgumentException("Username and password must not be null");
		}
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
