package com.example.agent.dto;

import com.example.agent.entity.AgentStatus;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentUpdateRequest {
    @Pattern(regexp = "(^$|[0-9]{10})", message = "Phone number must be 10 digits")
    private String phone;

    private AgentStatus status;
}