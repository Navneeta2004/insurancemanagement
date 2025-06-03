package com.cts.agentservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class AgentDTO {
    private Long agentId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Double totalCommissionEarned;
}