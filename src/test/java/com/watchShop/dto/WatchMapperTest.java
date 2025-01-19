package com.watchShop.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.watchShop.model.Image;
import com.watchShop.model.Watch;

class WatchMapperTest {
	
	private WatchMapper mapper;
	
	@BeforeEach
	void setUp() {
		this.mapper = new WatchMapper();
	}

	@Test
	public void testToWatchDto() {
		Image image = new Image();
		image.setId(24L);
		image.setPathToImage("/images/Seiko Alpinist.jpg");
		Watch watch = new Watch(24L, "Alpinist", "Seiko", new BigDecimal(725.00), "Automaat Cal. 6R35, 39.5mm, Stalen kast, WR20 bar, Safier", image);
		WatchDto dto = mapper.toWatchDto(watch);
		assertEquals(watch.getId(), dto.getId());
		assertEquals(watch.getName(), dto.getName());
		assertEquals(watch.getBrand(), dto.getBrand());
		assertEquals(watch.getPrice(), dto.getPrice());
		assertEquals(watch.getDescription(), dto.getDescription());
		assertEquals(watch.getImage().getId(), dto.getId());
	}
	
	@Test
	public void testToWatch() {
		WatchDto dto = new WatchDto(24L, "Alpinist", "Seiko", new BigDecimal(725.00), "Automaat Cal. 6R35, 39.5mm, Stalen kast, WR20 bar, Safier", 24L);
		Image image = new Image();
		image.setId(24L);
		image.setPathToImage("/images/Seiko Alpinist.jpg");
		Watch watch = mapper.toWatch(dto, image);
		assertEquals(dto.getId(), watch.getId());
		assertEquals(dto.getName(), watch.getName());
		assertEquals(dto.getBrand(), watch.getBrand());
		assertEquals(dto.getPrice(), watch.getPrice());
		assertEquals(dto.getDescription(), watch.getDescription());
		assertEquals(dto.getImageId(), watch.getImage().getId());
		
	}

}
