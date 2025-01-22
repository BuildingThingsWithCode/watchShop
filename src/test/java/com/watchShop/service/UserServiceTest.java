package com.watchShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.watchShop.model.Role;
import com.watchShop.model.User;
import com.watchShop.repository.RoleRepository;
import com.watchShop.repository.UserRepository;

class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private AuthenticationManager authenticationManager;

	@InjectMocks
	private UserService userService;

	private String username;
	private String password; 
	private String email;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		this.username = "testuser";
		this.password = "testpassword";
		this.email = "testuser@example.com";
		SecurityContextHolder.clearContext();
	}

	@Test
	void testRegisterUserSuccess() {
		// Arrange
		String encodedPassword = "encodedPassword";

		Role userRole = new Role();
		userRole.setId(1L);
		userRole.setName("USER");

		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
		when(passwordEncoder.encode(password)).thenReturn(encodedPassword);
		when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
		when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

		// Act
		User result = userService.registerUser(username, password, email);

		// Assert
		assertNotNull(result);
		assertEquals(username, result.getUsername());
		assertEquals(encodedPassword, result.getPassword());
		assertEquals(email, result.getEmail());

		Set<Role> expectedRoles = new HashSet<>();
		expectedRoles.add(userRole);
		assertEquals(expectedRoles, result.getRoles());

		// Verify interactions
		verify(userRepository, times(1)).findByUsername(username);
		verify(userRepository, times(1)).findByEmail(email);
		verify(passwordEncoder, times(1)).encode(password);
		verify(roleRepository, times(1)).findByName("USER");
		verify(userRepository, times(1)).save(result);
	}


	@Test
	void testRegisterUserUsernameAlreadyExistsFail() {
		// Arrange
		when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));

		// Act & Assert
		RuntimeException exception = assertThrows(RuntimeException.class, () ->
		userService.registerUser(username, password, email));
		assertEquals("Username already exists. Please choose another one.", exception.getMessage());

		verify(userRepository, times(1)).findByUsername(username);
		verify(userRepository, never()).findByEmail(email);
		verify(roleRepository, never()).findByName("USER");
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testRegisterUserEmailAlreadyExistsFail() {
		// Arrange
		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
		when(userRepository.findByEmail(email)).thenReturn(Optional.of(new User()));

		// Act & Assert
		RuntimeException exception = assertThrows(RuntimeException.class, () ->
		userService.registerUser(username, password, email));
		assertEquals("Email already exists. Please choose another one.", exception.getMessage());

		verify(userRepository, times(1)).findByUsername(username);
		verify(userRepository, times(1)).findByEmail(email);
		verify(roleRepository, never()).findByName("USER");
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testRegisterUserRoleUserNotFoundFail() {
		// Arrange
		when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
		when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
		when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

		// Act & Assert
		RuntimeException exception = assertThrows(RuntimeException.class, () ->
		userService.registerUser(username, password, email));
		assertEquals("Role USER not found", exception.getMessage());

		verify(userRepository, times(1)).findByUsername(username);
		verify(userRepository, times(1)).findByEmail(email);
		verify(roleRepository, times(1)).findByName("USER");
		verify(userRepository, never()).save(any(User.class));
	}

	@Test
	void testAuthenticateUserAuthenticationShouldSetSecurityContextSucces() {
		// Arrange
		Authentication authenticationMock = mock(Authentication.class);
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
		.thenReturn(authenticationMock);

		// Act
		userService.authenticateUser(username, password);

		// Assert
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		assertNotNull(authentication, "Authentication should be set in SecurityContext");
		assertEquals(authenticationMock, authentication, "SecurityContext should contain the authenticated token");
		verify(authenticationManager, times(1))
		.authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

	@Test
	void testAuthenticateUserInvalidCredentialsFail() {
		// Arrange
		when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
		.thenThrow(new RuntimeException("Invalid credentials"));

		// Act & Assert
		RuntimeException exception = assertThrows(RuntimeException.class, () ->
		userService.authenticateUser(username, password));
		assertEquals("Invalid credentials", exception.getMessage());

		assertNull(SecurityContextHolder.getContext().getAuthentication(), 
				"SecurityContext should not contain an authentication token on failure");

		verify(authenticationManager, times(1))
		.authenticate(any(UsernamePasswordAuthenticationToken.class));
	}

	@Test
	void testAuthenticateUserNullUsernameFail() {
		// Arrange
		String invalidUsername = null;

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () ->
		userService.authenticateUser(invalidUsername, password));
		assertNull(SecurityContextHolder.getContext().getAuthentication(), 
				"SecurityContext should not contain an authentication token when inputs are invalid");
		verifyNoInteractions(authenticationManager);
	}

	@Test
	void testAuthenticateUserNullPasswordFail() {
		// Arrange
		String invalidPassword = null;

		// Act & Assert
		assertThrows(IllegalArgumentException.class, () ->
		userService.authenticateUser(username, invalidPassword));

		assertNull(SecurityContextHolder.getContext().getAuthentication(), 
				"SecurityContext should not contain an authentication token when inputs are invalid");

		verifyNoInteractions(authenticationManager);
	}
}





