package com.cts.claimservice.dto;

import com.cts.claimservice.entity.ClaimStatus;

import lombok.Data;

@Data
public class ClaimStatusResponse {
    private Long claimId;
    private ClaimStatus status;
    private String message;
}
