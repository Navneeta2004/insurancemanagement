package com.cts.agentservice.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ClaimDTO {
    private Long claimId;
    private Long policyId;
    private Long customerId;
    private LocalDate dateFiled;
    private String claimReason;
    private Double requestedAmount;
    private String status;
}