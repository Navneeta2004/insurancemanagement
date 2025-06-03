package com.cts.customerservice.dto;

import lombok.Data;

@Data
public class ClaimStatusResponse {
    private Long claimId;
    private String status;
    private String details;
}
