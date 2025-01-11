package com.watchShop.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Form {
	
	@NotBlank(message="This field is required.")
	@Size(min=2, max=45, message="Username must be between 2 and 45 characters.")
	private String username;
	@NotBlank(message="This field is required.")
	@Size(min=2, max=45, message="Password must be between 2 and 45 characters.")
	private String password;
}
