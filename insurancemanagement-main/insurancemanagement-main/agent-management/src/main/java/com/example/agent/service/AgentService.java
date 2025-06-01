package com.example.agent.service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


import org.springframework.stereotype.Service;

import com.example.agent.clients.ClaimClient;
import com.example.agent.clients.CustomerClient;
import com.example.agent.clients.PolicyClient;
import com.example.agent.dto.AgentRequestDTO;
import com.example.agent.dto.AgentResponseDTO;
import com.example.agent.entity.Agent;
import com.example.agent.entity.AgentStatus;
import com.example.agent.exception.AgentNotFoundException;
import com.example.agent.repository.AgentRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AgentService {

    private final AgentRepository agentRepository;
    private final CustomerClient customerClient;
    private final PolicyClient policyClient;
    private final ClaimClient claimClient;

    public Agent createAgent(AgentRequestDTO dto) {
        log.info("Creating agent: {}", dto);
        Agent agent = Agent.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .assignedCustomerIds(dto.getAssignedCustomerIds())
                .status(AgentStatus.ACTIVE)
                .build();
        return agentRepository.save(agent);
    }

    public AgentResponseDTO getAgentDetails(Long agentId) {
        log.info("Fetching agent details for ID: {}", agentId);
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found with ID: " + agentId));

        List<Map<String, Object>> customers = customerClient.getCustomersByAgent(agentId);
        List<Map<String, Object>> policies = policyClient.getPoliciesByAgent(agentId);
        List<Map<String, Object>> claims = claimClient.getClaimsByAgent(agentId);

        double totalCommission = calculateCommission(policies);

        return AgentResponseDTO.builder()
                .agentId(agent.getAgentId())
                .firstName(agent.getFirstName())
                .lastName(agent.getLastName())
                .email(agent.getEmail())
                .phone(agent.getPhone())
                .status(agent.getStatus().name())
                .assignedCustomerIds(agent.getAssignedCustomerIds())
                .customers(customers)
                .policies(policies)
                .claims(claims)
                .totalCommission(totalCommission)
                .build();
    }

    public Agent updateAgent(Long agentId, AgentRequestDTO dto) {
        log.info("Updating agent ID: {}", agentId);
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found with ID: " + agentId));

        agent.setFirstName(dto.getFirstName());
        agent.setLastName(dto.getLastName());
        agent.setEmail(dto.getEmail());
        agent.setPhone(dto.getPhone());
        agent.setAssignedCustomerIds(dto.getAssignedCustomerIds());

        return agentRepository.save(agent);
    }

    public void deleteAgent(Long agentId) {
        log.info("Deleting agent ID: {}", agentId);
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found with ID: " + agentId));
        agentRepository.delete(agent);
    }

    public Map<String, Object> renewPolicy(Long agentId, Long policyId) {
        log.info("Agent ID: {} renewing policy ID: {}", agentId, policyId);
        return policyClient.renewPolicy(policyId);
    }

    public Map<String, Object> processClaim(Long agentId, Long claimId) {
        log.info("Agent ID: {} processing claim ID: {}", agentId, claimId);
        return claimClient.processClaim(claimId);
    }

    public List<Map<String, Object>> getCustomersByAgent(Long agentId) {
        log.info("Fetching customers for agent ID: {}", agentId);
        agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found with ID: " + agentId));
        return customerClient.getCustomersByAgent(agentId);
    }

    private double calculateCommission(List<Map<String, Object>> policies) {
        double commissionRate = 0.05;
        double totalCommission = 0.0;
        for (Map<String, Object> policy : policies) {
            Object premiumObj = policy.get("premium");
            if (premiumObj instanceof Number) {
                double premium = ((Number) premiumObj).doubleValue();
                totalCommission += premium * commissionRate;
            }
        }
        return totalCommission;
    }
}
