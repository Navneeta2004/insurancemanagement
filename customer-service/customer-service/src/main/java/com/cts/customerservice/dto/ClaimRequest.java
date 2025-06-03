package com.cts.customerservice.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClaimRequest{
    private Long customerId;
    private Long policyId;
    private String description;
    private LocalDate incidentDate;

}
