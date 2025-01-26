package com.watchShop.controller;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

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
	}
	
	// to handle returning a view, a view resolver is necessary in the MockMvc.
		private void setupWithViewResolver() {
			this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
					.setViewResolvers((viewName, locale) -> {
						return new InternalResourceView("/path/to/views/" + viewName + ".html");
					})
					.build();
		}

		// to handle a redirect, there should not be a view resolver in the MockMvc.
		private void setupWithoutViewResolver() {
			this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		}
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
