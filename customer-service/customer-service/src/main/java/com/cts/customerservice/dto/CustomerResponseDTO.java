package com.cts.customerservice.dto;

import lombok.Data;

@Data
public class CustomerResponseDTO {
	    private Long id;
	    private String email;
	    private String phone;
	    private String name;
	    private String address;

}
