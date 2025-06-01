package com.example.agent.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Agent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long agentId;  // Changed from id to agentId

    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    @Enumerated(EnumType.STRING)
    private AgentStatus status = AgentStatus.ACTIVE;

    @ElementCollection
    @CollectionTable(name = "agent_customers", joinColumns = @JoinColumn(name = "agent_id"))
    @Column(name = "customer_id")
    @Builder.Default
    private List<Long> assignedCustomerIds = new ArrayList<>();
}
