
package com.example.agent.dto;

import com.example.agent.entity.AgentStatus;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AgentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private AgentStatus status;
    private String territoryCode;
}
