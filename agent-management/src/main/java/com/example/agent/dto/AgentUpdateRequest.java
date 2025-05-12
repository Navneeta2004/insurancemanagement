
package com.example.agent.dto;

import com.example.agent.entity.AgentStatus;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentUpdateRequest {
    private String phone;
    private AgentStatus status;
}
