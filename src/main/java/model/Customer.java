package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Customer {
	private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String city;
    private String postalCode;
    private String country;
   }
