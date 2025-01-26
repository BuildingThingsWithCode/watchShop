package com.watchShop.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.watchShop.model.Watch;
import com.watchShop.service.CartService;
import com.watchShop.service.QuoteService;
import com.watchShop.service.UserService;
import com.watchShop.service.WatchService;

class UserControllerTest {

	@Mock
	private UserService userService;

	@Mock
	private WatchService watchService;

	@Mock
	private QuoteService quoteService;

	@Mock
	private CartService cartService;

	@InjectMocks
	private UserController userController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}

	@Test
	void testHome() throws Exception {
		// Arrange
		List<Watch> mockWatchList = new ArrayList<>();
		String mockSortOrder = "desc";
		when(watchService.findAllByOrder(mockSortOrder)).thenReturn(mockWatchList);

		// Act & Assert
		mockMvc.perform(get("/").param("sortOrder", mockSortOrder))
		.andExpect(status().isOk())
		.andExpect(view().name("home"))
		.andExpect(model().attribute("watches", mockWatchList))
		.andExpect(model().attribute("sortOrder", mockSortOrder));

		verify(watchService, times(1)).findAllByOrder(mockSortOrder);
		verifyNoMoreInteractions(watchService);
	}

	@Test
	void testShowLoginPageAuthenticatedUser() throws Exception {
		// Arrange
		SecurityContext mockSecurityContext = mock(SecurityContext.class);
		Authentication mockAuthentication = mock(Authentication.class);
		when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
		when(mockAuthentication.isAuthenticated()).thenReturn(true);
		SecurityContextHolder.setContext(mockSecurityContext);

		// Act & Assert
		mockMvc.perform(get("/login"))
		.andExpect(status().isOk())
		.andExpect(view().name("login2"))
		.andExpect(model().attribute("authentication", true));
	}

	@Test
	public void testShowLoginPageAnonymousUser() throws Exception {
		// Arrange
		SecurityContext mockSecurityContext = mock(SecurityContext.class);
		Collection<GrantedAuthority> mockRoles = new ArrayList<>();
		mockRoles.add(new SimpleGrantedAuthority("ROLE_ANONYMOUS"));
		Authentication anonymousAuth = new AnonymousAuthenticationToken("key", "principal", mockRoles);
		when(mockSecurityContext.getAuthentication()).thenReturn(anonymousAuth);
		SecurityContextHolder.setContext(mockSecurityContext);

		// Act & Assert
		mockMvc.perform(get("/login"))
		.andExpect(status().isOk())
		.andExpect(view().name("login2"))
		.andExpect(model().attribute("authentication", false));
	}

}
