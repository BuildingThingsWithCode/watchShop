package com.watchShop.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchDTO {
	
	    private String name;
	    private String brand;
	    private BigDecimal price;
	    private String description;
	    private Long imageId;
	

}
