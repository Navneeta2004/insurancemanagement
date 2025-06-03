package com.cts.agentservice.dto;

import lombok.Data;

@Data
public class ClaimVerificationRequest {
    private Long claimId;
    private String comments;
    private boolean verified;
}
