package com.watchShop.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.watchShop.model.User;
import com.watchShop.repository.UserRepository;

class MyUserDetailsServiceTest {
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private MyUserDetailsService userDetailsService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testLoadUserByUsernameSucces() {
		// Arrange
		String username = "John Doe";
		User mockUser = mock(User.class); 
		when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(mockUser));
		
		// Act
		//User result = userDetailsService.loadUserByUsername(username);
	}
	
	@Test
	public void testLoadUserByUsernameFail() {
		String username = "John Doe";
	}

}
