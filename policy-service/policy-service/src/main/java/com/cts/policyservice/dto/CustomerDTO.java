package com.cts.policyservice.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long customerid;
    private String email;
    private String phone;
    private String name;
    private String address;
}