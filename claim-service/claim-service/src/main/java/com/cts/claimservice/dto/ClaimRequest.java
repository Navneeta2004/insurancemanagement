package com.cts.claimservice.dto;


import lombok.Data;
import java.time.LocalDate;

@Data
public class ClaimRequest {
    private Long policyId;
    private Long customerId;
    private String claimReason;
    private Double requestedAmount;
    private LocalDate accidentDate;
    private String accidentLocation;
    private String accidentDescription;
}
