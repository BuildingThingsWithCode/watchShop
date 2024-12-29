package model;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Watch {
	
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	// movement, caseSize, height, lugToLug, lugWidth, color, waterResistance, crystal
	// caseMaterial, bandMaterial -> these all go in description.
	private String description;
}
