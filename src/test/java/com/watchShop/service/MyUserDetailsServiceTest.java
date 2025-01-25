package com.watchShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.watchShop.model.Role;
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
		Set<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setName("USER");
		roles.add(role);
		
		User mockUser = mock(User.class);
		mockUser.setUsername(username);
		mockUser.setPassword("password");
		mockUser.setRoles(roles);
		when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
	    when(mockUser.getRoles()).thenReturn(roles);
		
		// Act
		UserDetails result = userDetailsService.loadUserByUsername(username);
		
		// Assert
		assertNotNull(result);
		assertEquals(mockUser.getUsername(), result.getUsername());
		assertEquals(mockUser.getPassword(), result.getPassword());
		assertEquals(1, result.getAuthorities().size()); 
	    assertTrue(result.getAuthorities().contains(new SimpleGrantedAuthority("USER"))); 
	}
	
	@Test
	public void testLoadUserByUsernameFail() {
		// Arrange
		String username = "NoneExistingUser";
		String message = "User with username " + username + "was not found";
		UsernameNotFoundException mockException = new UsernameNotFoundException(message);
		when(userRepository.findByUsername(username)).thenThrow(mockException);
		
		// Act & Assert
		UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(username));
		
		// Assert
		assertEquals("User with username " + username + "was not found", exception.getMessage());
		verify(userRepository).findByUsername(any(String.class));
	}

}
