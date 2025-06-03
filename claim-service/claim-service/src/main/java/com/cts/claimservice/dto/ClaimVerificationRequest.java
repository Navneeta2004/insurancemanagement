package com.cts.claimservice.dto;

import lombok.Data;

@Data
public class ClaimVerificationRequest {
    private Long claimId;
    private String verificationComments;
    private boolean verified;
}