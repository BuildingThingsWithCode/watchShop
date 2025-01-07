package com.watchShop.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.watchShop.model.AppUser;
import com.watchShop.model.Role;
import com.watchShop.repository.AppUserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements UserDetailsService {

	private final AppUserRepository appUserRepository;
	private final PasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) {
		AppUser appUser = appUserRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User with username " + username + "was not found"));
		return User.builder()
		.username(appUser.getUsername())
		.password(appUser.getPassword())
		.build();
		
	}
	
	public AppUser saveUser(String username, String password, String email) {
		AppUser user = new AppUser();
		user.setUsername(username);
		user.setPassword(passwordEncoder.encode(password));
		user.setEmail(email);
		Set<Role> roles = new HashSet<>();
		roles.add(new Role("USER"));
		user.setRoles(roles);
		return appUserRepository.save(user);
	}

}
