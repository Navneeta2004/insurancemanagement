package com.cts.policyservice.dto;

import lombok.Data;

@Data
public class PolicyRenewalRequest {
    private Long customerId;
    private Long policyId;
    private Double insuredValue;
}
