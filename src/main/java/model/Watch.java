package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Watch {
	
	private String brand;
	private String movement;
	private double caseSize;
	private double height;
	private double lugToLug;
	private double lugWidth;
	private double color;
	private int waterResistance;
	private String crystal;
	private String caseMaterial;
	private String bandMaterial;
}
