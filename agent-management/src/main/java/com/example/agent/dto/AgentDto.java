package com.example.agent.dto;

import com.example.agent.entity.Agent;
import com.example.agent.entity.AgentStatus;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AgentStatus status;
    private List<String> assignedPolicies;

    public static AgentDto fromEntity(Agent agent) {
        return new AgentDto(
                agent.getId(),
                agent.getFirstName(),
                agent.getLastName(),
                agent.getEmail(),
                agent.getPhone(),
                agent.getStatus(),
                agent.getAssignedPolicies()
        );
    }
}
