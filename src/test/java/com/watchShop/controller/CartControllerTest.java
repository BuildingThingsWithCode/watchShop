package com.watchShop.controller;

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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

import com.watchShop.model.Watch;
import com.watchShop.service.CartService;
import com.watchShop.service.WatchService;

class CartControllerTest {

	@Mock
	private CartService cartService;

	@Mock 
	private WatchService watchService;

	@InjectMocks
	private CartController cartController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
	}

	@Test
	void testGetCart() throws Exception {
		// Arrange
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
		.andExpect(view().name("cart2"))
		.andExpect(model().attribute("cartItems", mockCartItems))
		.andExpect(model().attribute("totalPrice", mockTotal));

		verify(cartService, times(1)).getAll();
		verify(cartService, times(1)).getTotal();
		verifyNoMoreInteractions(cartService);
	}

	@Test
	void testAddToCart() throws Exception {
		// Arrange
		Long mockWatchId = 1L;
		Watch mockWatch = mock(Watch.class);
		when(watchService.getWatchById(mockWatchId)).thenReturn(mockWatch);

		// Act & Assert
		mockMvc.perform(post("/add-to-cart").param("watchId", mockWatchId.toString()))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/cart"));

		verify(watchService, times(1)).getWatchById(mockWatchId);
		verify(cartService, times(1)).add(mockWatch);
		verifyNoMoreInteractions(watchService, cartService);
	}

	@Test
	void testDeleteFromCartItemsPresent() throws Exception {
		// Arrange
		List<Long> mockCartItemIds = Arrays.asList(1L, 4L, 6L);

		// Act & Assert
		mockMvc.perform(post("/delete-from-cart").param("deleteItems", "1", "4", "6"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/cart"));

		verify(cartService, times(1)).removeItems(mockCartItemIds);
		verifyNoMoreInteractions(cartService);
	}

	@Test
	void testDeleteFromCartEmptyItems() throws Exception {
		// Act & Assert
		mockMvc.perform(post("/delete-from-cart").param("deleteItems", ""))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/cart"));

		verifyNoInteractions(cartService);
	}

	@Test
	void testDeleteFromCartNoItems() throws Exception {
		// Act & Assert
		mockMvc.perform(post("/delete-from-cart"))
		.andExpect(status().isFound())
		.andExpect(redirectedUrl("/cart"));

		verifyNoInteractions(cartService);
	}
}
