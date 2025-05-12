
package com.example.agent.dto;

import com.example.agent.entity.AgentStatus;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AgentCreateRequest {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    private String phone;

    @NotNull
    private AgentStatus status;
}
