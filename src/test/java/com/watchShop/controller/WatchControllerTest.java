package com.watchShop.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceView;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.watchShop.model.Image;
import com.watchShop.model.Watch;
import com.watchShop.service.ImageService;
import com.watchShop.service.WatchService;

class WatchControllerTest {

	@Mock
	private WatchService watchService;

	@Mock
	private ImageService imageService;

	@InjectMocks
	private WatchController watchController;

	private MockMvc mockMvc;

	@BeforeEach
	void setup() {
		MockitoAnnotations.openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(watchController).build();
	}

	@Test
	void testGetInfoPage() throws Exception {
		// Arrange
		Long mockWatchid = 1L;
		Watch mockWatch = mock(Watch.class);
		Image mockImage = mock(Image.class);
		mockWatch.setImage(mockImage);
		String mockPathToImage = "/path/to/image.jpg";
		when(watchService.getWatchById(mockWatchid)).thenReturn(mockWatch);
		when(mockWatch.getImage()).thenReturn(mockImage);
		when(mockImage.getPathToImage()).thenReturn(mockPathToImage);
		
		// Act & Assert
		mockMvc.perform(get("/watch/{id}", mockWatchid))
		.andExpect(status().isOk())
		.andExpect(view().name("info"))
		.andExpect(model().attribute("watch", mockWatch))
		.andExpect(model().attribute("image", "/" + mockPathToImage));
		
		verify(watchService, times(1)).getWatchById(mockWatchid);
		Mockito.verifyNoMoreInteractions(watchService);
	}

}
