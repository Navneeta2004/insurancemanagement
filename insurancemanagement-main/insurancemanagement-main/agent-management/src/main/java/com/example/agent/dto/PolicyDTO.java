package com.example.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PolicyDTO {
    private Long policyId;
    private String policyType;
    private String startDate;
    private String endDate;
    private Long customerId;

}
