package com.cts.claimservice.dto;
import lombok.Data;
import java.time.LocalDate;

import com.cts.claimservice.entity.ClaimStatus;

@Data
public class ClaimResponse {
    private Long claimId;
    private Long policyId;
    private LocalDate dateFiled;
    private String claimReason;
    private Double requestedAmount;
    private ClaimStatus status;
    private LocalDate accidentDate;
    private String accidentLocation;
    private String accidentDescription;
}
