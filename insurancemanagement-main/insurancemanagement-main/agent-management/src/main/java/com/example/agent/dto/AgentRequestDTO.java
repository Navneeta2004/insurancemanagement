package com.example.agent.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<Long> assignedCustomerIds;
}
