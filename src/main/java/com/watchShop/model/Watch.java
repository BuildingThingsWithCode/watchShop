package com.watchShop.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data 
@NoArgsConstructor 
@AllArgsConstructor
@Table(name = "watch")
@Builder
public class Watch {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String brand;
	private BigDecimal price;
	private String description;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "image_id")
	private Image image;
}
