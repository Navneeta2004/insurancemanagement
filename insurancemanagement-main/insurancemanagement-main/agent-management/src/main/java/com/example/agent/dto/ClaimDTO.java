package com.example.agent.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ClaimDTO {
	
	private Long claimId;
    private Long policyId;
    private Long customerId;
    private String status;
    private String claimDate;
    private String description;

}
