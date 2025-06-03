package com.cts.policyservice.dto;


import lombok.Data;

@Data
public class AgentDTO {
    private Long agentid;
    private String name;
    private String phone;
    private String email;
}
