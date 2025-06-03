package com.cts.claimservice.dto;

import lombok.Data;

@Data
public class AgentDTO {
    private Long agentId;
    private String name;
    private String phone;
    private String email;
}