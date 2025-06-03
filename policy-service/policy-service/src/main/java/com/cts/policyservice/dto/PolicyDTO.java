package com.cts.policyservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PolicyDTO {
    private Long policyid;
    private String policyName;
    private Double premium;
    private String renewal;
    private String coverageDetails;
    private LocalDate validityPeriod;
    private Double insuredValue;
    private Long customerId;
    private Long agentId;
}
