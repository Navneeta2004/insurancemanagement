
package com.example.agent.service;

import com.example.agent.dto.*;

import java.util.List;

public interface AgentService {
    AgentDto createAgent(AgentCreateRequest request);
    AgentDto updateAgent(Long id, AgentUpdateRequest request);
    AgentDto getAgentById(Long id);
    List<AgentDto> getAllAgents();
    void deleteAgent(Long id);
    AgentDto assignPolicies(Long id, List<String> policies);
    void alignAgentToTerritory(Long id, String territoryCode);
    List<String> getAgentCustomers(Long id);
    double calculateCommission(Long id);
}
