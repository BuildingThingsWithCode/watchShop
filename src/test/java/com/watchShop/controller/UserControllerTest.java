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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.watchShop.model.Form;
import com.watchShop.model.Watch;
import com.watchShop.service.CartService;
import com.watchShop.service.QuoteService;
import com.watchShop.service.UserService;
import com.watchShop.service.WatchService;

//@Import(ProjectConfig.class)
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

	@AfterEach
	public void clearContext() {
		SecurityContextHolder.clearContext();
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
	public void testLoginUserFail() throws Exception {
		// Act & Assert
		mockMvc.perform(post("/login")
				.param("username", "")
				.param("password", "thePassword"))
		.andExpect(status().isOk())
		.andExpect(view().name("login2"))
		.andExpect(model().attributeExists("form"))
		.andExpect(model().attributeHasFieldErrors("form", "username"))
		.andExpect(result -> {
			Form form = (Form) result.getModelAndView().getModel().get("form");
			assertNotNull(form);
			assertEquals("", form.getUsername());
			assertEquals("thePassword", form.getPassword());
		});

		verifyNoInteractions(userService);
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

	@Test
	public void testCreateAndSaveUserSuccess() throws Exception {
		// Act & Assert
		mockMvc.perform(post("/register")
				.param("username", "theUsername")
				.param("email", "email@somewhere.com")
				.param("password", "thePassword")
				.param("confirm", "thePassword"))
		.andExpectAll(status().isFound())
		.andExpectAll(redirectedUrl("/"));

		verify(userService, times(1)).registerUser("theUsername", "thePassword", "email@somewhere.com");
		verify(userService, times(1)).authenticateUser("theUsername", "thePassword");

	}

	@Test
	public void testCreateAndSaveUserFail() throws Exception {
		// Act & Assert
		mockMvc.perform(post("/register")
				.param("username", "") 
				.param("email", "invalid-email")
				.param("password", "")
				.param("confirm", "abc"))
		.andExpect(status().isOk())
		.andExpect(view().name("register2"))
		.andExpect(model().attributeExists("form"))
		.andExpect(model().attributeHasFieldErrors("form", "username", "email", "password"));

		verifyNoInteractions(userService);
	}

	@Test
	//@WithMockUser(username = "admin", roles = "ADMIN")
	public void testShowAdminPageSuccess() throws Exception {
		// Arrange
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("admin", "password", 
				AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		Map<String, String> mockMap = new HashMap<>();
		mockMap.put("q", "this is a quote");
		mockMap.put("a", "this is the author");
		when(quoteService.getQuote()).thenReturn(mockMap);

		// Act & Assert
		mockMvc.perform(get("/admin"))
		.andExpect(status().isOk())
		.andExpect(view().name("admin2"))
		.andExpect(model().attribute("quote", "this is a quote"))
		.andExpect(model().attribute("author", "this is the author"));

		verify(quoteService, times(1)).getQuote();
	}

	@Test
	void testShowAdminPageNoAdminCredentialsFail() throws Exception {
		// Arrange
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken("user", "password", 
				AuthorityUtils.createAuthorityList("ROLE_USER"));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		System.out.println("///////////////////////////////////////////"+SecurityContextHolder.getContext().getAuthentication().getCredentials());
		mockMvc.perform(get("/admin"))
		.andExpect(status().is3xxRedirection())
		.andExpect(redirectedUrl("/noaccess"));
	}
}

//	@Test
//	void testShowAdminPageNotAuthenticated() throws Exception {
//	    mockMvc.perform(get("/admin"))
//	            .andExpect(status().is3xxRedirection()) // Expect a redirection
//	            .andExpect(redirectedUrl("/login")); // Expect redirection to the login page
//	}








//@GetMapping("/admin")
//public String showAdminPage(Model model) {
//	Map<String, String> quote = quoteService.getQuote();
//	model.addAttribute("quote", quote.get("q"));
//	model.addAttribute("author", quote.get("a"));
//	return "admin";
//}