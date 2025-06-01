package com.example.agent.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AgentResponseDTO {
	
	 private Long agentId;
	    private String firstName;
	    private String lastName;
	    private String email;
	    private String phone;
	    private String status;
	    private List<Long> assignedCustomerIds;

	    // Detailed info from other modules via Feign clients
	    private List<Map<String, Object>> customers;
	    private List<Map<String, Object>> policies;
	    private List<Map<String, Object>> claims;

	    private double totalCommission;

}
