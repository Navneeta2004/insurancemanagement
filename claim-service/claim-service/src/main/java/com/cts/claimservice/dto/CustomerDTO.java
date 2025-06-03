package com.cts.claimservice.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long customerId;
    private String email;
    private String phone;
    private String name;
    private String address;
}
