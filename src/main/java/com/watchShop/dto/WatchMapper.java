package com.watchShop.dto;

import org.springframework.stereotype.Component;

import com.watchShop.model.Image;
import com.watchShop.model.Watch;

@Component
public class WatchMapper {
	
	public WatchDto toWatchDto(Watch watch) {
        return new WatchDto(
        		watch.getId(), 
        		watch.getName(), 
        		watch.getBrand(),
        		watch.getPrice(), 
        		watch.getDescription(),
        		watch.getImage().getId());
	}
	
	public Watch toWatch(WatchDto dto, Image image) {
        Watch watch = new Watch();
        watch.setId(dto.getId());
        watch.setName(dto.getName());
        watch.setBrand(dto.getBrand());
        watch.setPrice(dto.getPrice());
        watch.setDescription(dto.getDescription());
        watch.setImage(image);
        return watch;
    }
}