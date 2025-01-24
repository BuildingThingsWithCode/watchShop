package com.watchShop.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.lang.ProcessBuilder.Redirect;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;

import com.watchShop.model.Watch;
import com.watchShop.service.CartService;
import com.watchShop.service.WatchService;

class CartControllerTest {

	@Mock
	private CartService cartService;

	@Mock 
	private WatchService watchService;

	//	@Mock
	//	private Model model;

	@InjectMocks
	private CartController cartController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
	}
	
	// to handle returning a view, a view resolver is necessary in the MockMvc.
	private void setupWithViewResolver() {
		this.mockMvc = MockMvcBuilders.standaloneSetup(cartController)
				.setViewResolvers((viewName, locale) -> {
				    return new InternalResourceView("/path/to/views/" + viewName + ".html");
				})
				.build();
    }
	
	// to handle a redirect, there should not be a view resolver in the MockMvc.
    private void setupWithoutViewResolver() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

	@Test
	void testGetCart() throws Exception {
		// Arrange
		setupWithViewResolver();
		Watch watch1 = new Watch();
		Watch watch2 = new Watch();
		Map<Watch, Integer> map = new HashMap<>();
		map.put(watch1, 1);
		map.put(watch2, 3);
		Set<Entry<Watch, Integer>> mockCartItems = map.entrySet();
		BigDecimal mockTotal = BigDecimal.valueOf(1350.00);
		when(cartService.getAll()).thenReturn(mockCartItems);
		when(cartService.getTotal()).thenReturn(mockTotal);

		// Act & Assert
		mockMvc.perform(get("/cart"))
		.andExpect(status().isOk())
		.andExpect(view().name("cart"))
		.andExpect(model().attribute("cartItems", mockCartItems))
		.andExpect(model().attribute("totalPrice", mockTotal));
		
		verify(cartService, times(1)).getAll();
		verify(cartService, times(1)).getTotal();
		verifyNoMoreInteractions(cartService);
	}
	
	@Test
	void testAddToCart() throws Exception {
		// Arrange
		setupWithoutViewResolver();
		Long mockWatchId = 1L;
		Watch mockWatch = mock(Watch.class);
		when(watchService.getWatchById(mockWatchId)).thenReturn(mockWatch);
		
		// Act & Assert
		mockMvc.perform(post("/addToCart").param("watchId", mockWatchId.toString()))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/cart"));
		
		verify(watchService, times(1)).getWatchById(mockWatchId);
		verify(cartService, times(1)).add(mockWatch);
		verifyNoMoreInteractions(watchService, cartService);
	}

}
