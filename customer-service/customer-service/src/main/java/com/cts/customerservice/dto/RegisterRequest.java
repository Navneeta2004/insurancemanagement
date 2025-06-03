package com.cts.customerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	    private Long customerid;
	    private String name;
	    private String email;
	    private String phone;
	    private String address;
}
