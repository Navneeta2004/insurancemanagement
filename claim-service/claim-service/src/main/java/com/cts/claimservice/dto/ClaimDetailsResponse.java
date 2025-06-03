package com.cts.claimservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClaimDetailsResponse {
    private Long claimId;
    private Long policyId;
    private Long customerId;
    private LocalDate dateFiled;
    private String claimReason;
    private Double requestedAmount;
    private String status;
    private LocalDate accidentDate;
    private String accidentLocation;
    private String accidentDescription;
    private Long agentId;
    private boolean verified;
    private String verificationComments;
}
