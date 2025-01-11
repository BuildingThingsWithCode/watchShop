package com.watchShop.model;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class RegisterForm {

    @NotBlank(message = "Required field.")
    @Size(min = 2, max = 45, message = "Between (2 - 45) characters.")
    private String username;

    @NotBlank(message = "Required field.")
    @Email(message = "Must be a valid email")
    private String email;

    @NotBlank(message = "Required field.")
    @Size(min = 2, max = 45, message = "Between (2 - 45) characters.")
    private String password;

    @NotBlank(message = "Required field.")
    private String confirm;

    @AssertTrue(message = "Passwords must match.")
    public boolean isPasswordsMatching() {
        return password != null && password.equals(confirm);
    }
}
