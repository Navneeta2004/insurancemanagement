package com.cts.customerservice.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class PolicyDTO {
	private Long policyid;
    private String policyName;
    private String coverageDetails;
    private Double premium;
    private LocalDate validityPeriod;
}
