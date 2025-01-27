package com.watchShop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Collection;
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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.watchShop.model.Form;
import com.watchShop.model.RegisterForm;
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

	@Test
	public void testLoginUserFail() throws Exception {
		// Act & Assert
		mockMvc.perform(post("/login")
                .param("username", "")
                .param("password", "thePassword"))
        .andExpect(status().isOk())
        .andExpect(view().name("login2"))
        .andExpect(model().attributeExists("form"))
        .andExpect(result -> {
            Form form = (Form) result.getModelAndView().getModel().get("form");
            assertNotNull(form);
            assertEquals("", form.getUsername());
            assertEquals("thePassword", form.getPassword());
        });

		verifyNoInteractions(userService);
	}
	
	@Test
	public void testLoginUserSuccess() throws Exception {
		// Act & Assert
		mockMvc.perform(post("/login")
                .param("username", "theUsername")
                .param("password", "thePassword"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/"));

		verify(userService, times(1)).authenticateUser("theUsername", "thePassword");
	}
	
	@Test
	public void testShowRegisterPageUserAlreadyAuthenticated() throws Exception {
		 // Arrange
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(true);
        SecurityContextHolder.setContext(mockSecurityContext);

        // Act & Assert
        mockMvc.perform(get("/register"))
        		.andExpect(status().isOk())
        		.andExpect(view().name("register2"))
        		.andExpectAll(model().attribute("authentication", true))
        		.andExpect(model().attributeDoesNotExist("form"));
   
        verify(mockSecurityContext).getAuthentication();
	}
	
	@Test
	public void testShowRegisterPageUserNotAuthenticated() throws Exception {
		 // Arrange
        SecurityContext mockSecurityContext = mock(SecurityContext.class);
        Authentication mockAuthentication = mock(Authentication.class);
        when(mockSecurityContext.getAuthentication()).thenReturn(mockAuthentication);
        when(mockAuthentication.isAuthenticated()).thenReturn(false);
        SecurityContextHolder.setContext(mockSecurityContext);

        // Act & Assert
        mockMvc.perform(get("/register"))
        		.andExpect(status().isOk())
        		.andExpect(view().name("register2"))
        		.andExpectAll(model().attribute("authentication", false))
        		.andExpect(model().attributeExists("form"));
   
        verify(mockSecurityContext).getAuthentication();
	}
}

//@GetMapping("/register")
//public String showRegisterPage(Model model) {
//	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//	boolean isAuthenticated = false;
//	if (authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
//		isAuthenticated = true;
//	}
//	model.addAttribute("authentication", isAuthenticated);
//	model.addAttribute("form", new RegisterForm());
//	return "register"; 
//}