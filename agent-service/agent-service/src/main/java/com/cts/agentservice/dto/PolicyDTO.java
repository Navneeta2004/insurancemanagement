package com.cts.agentservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PolicyDTO {
    private Long policyId;
    private String policyName;
    private Double premium;
    private LocalDate validityPeriod;
    private Double insuredValue;
    private Long customerId;
    private Long agentId;
}