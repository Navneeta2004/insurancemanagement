package com.cts.policyservice.dto;

import lombok.Data;

@Data
public class PolicyRenewalResponse {
    private Long policyId;
    private String status;
    private String message;
}