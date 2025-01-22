package com.watchShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

class QuoteServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private QuoteService quoteService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testGetQuoteSuccess() {
		// Arrange
		Map<String, String> mockQuote = Collections.singletonMap("quote", "This is a quote.");
		when(restTemplate.getForObject("https://zenquotes.io/api/today", List.class))
		.thenReturn(Collections.singletonList(mockQuote));

		// Act
		Map<String, String> result = quoteService.getQuote();

		// Assert
		assertNotNull(result);
		assertEquals("This is a quote.", result.get("quote"));
	}

	@Test
	void testGetQuoteEmptyList() {
		// Arrange
		when(restTemplate.getForObject("https://zenquotes.io/api/today", List.class))
		.thenReturn(Collections.emptyList());

		// Act
		Map<String, String> result = quoteService.getQuote();

		// Assert
		assertNotNull(result);
		assertEquals("No quote available today.", result.get("quote"));
	}

	@Test
	void testGetQuoteException() {
		// Arrange
		when(restTemplate.getForObject("https://zenquotes.io/api/today", List.class))
		.thenThrow(new RuntimeException("Connection error"));

		// Act
		Map<String, String> result = quoteService.getQuote();

		// Assert
		assertNotNull(result);
		assertEquals("No quote available today.", result.get("quote"));
	}
}
