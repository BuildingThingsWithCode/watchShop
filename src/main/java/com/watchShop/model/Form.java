package com.watchShop.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Form {
	
	@NotBlank(message="Required field.")
	@Size(min=2, max=45, message="Between (2 - 45) characters.")
	private String username;
	@NotBlank(message="Required field.")
	@Size(min=2, max=45, message="Between (2 - 45) characters.")
	private String password;
}
