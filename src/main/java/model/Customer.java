package model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@NoArgsConstructor 
@AllArgsConstructor 
public class Customer {
	private UUID customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String shippingAddress;
    private String city;
    private String postalCode;
    private String country;
   }
