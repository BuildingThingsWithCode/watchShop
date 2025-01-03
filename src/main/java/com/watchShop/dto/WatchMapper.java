package com.watchShop.dto;

import org.springframework.stereotype.Component;

import com.watchShop.model.Image;
import com.watchShop.model.Watch;

@Component
public class WatchMapper {
	
	public WatchDTO toDto(Watch watch) {
        return new WatchDTO(
        		watch.getId(), 
        		watch.getName(), 
        		watch.getBrand(),
        		watch.getPrice(), 
        		watch.getImage().getId());
	}
	
	public Watch toEntity(WatchDTO dto, Image image) {
        Watch watch = new Watch();
        watch.setId(dto.getId());
        watch.setName(dto.getName());
        watch.setBrand(dto.getBrand());
        watch.setPrice(dto.getPrice());
        watch.setImage(image);
        return watch;
    }
}
