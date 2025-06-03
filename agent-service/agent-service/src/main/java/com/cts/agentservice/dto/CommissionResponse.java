package com.cts.agentservice.dto;

import lombok.Data;

@Data
public class CommissionResponse {
    private Long agentId;
    private Double totalCommissionEarned;
    private int policiesSold;
}