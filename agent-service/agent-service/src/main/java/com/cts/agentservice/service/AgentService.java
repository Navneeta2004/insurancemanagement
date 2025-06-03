package com.cts.agentservice.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cts.agentservice.client.ClaimClient;
import com.cts.agentservice.client.CustomerClient;
import com.cts.agentservice.client.PolicyClient;
import com.cts.agentservice.dto.AgentDTO;
import com.cts.agentservice.dto.ClaimDTO;
import com.cts.agentservice.dto.ClaimVerificationRequest;
import com.cts.agentservice.dto.CommissionResponse;
import com.cts.agentservice.dto.CustomerDTO;
import com.cts.agentservice.dto.PolicyDTO;
import com.cts.agentservice.entity.Agent;
import com.cts.agentservice.exception.AgentNotFoundException;
import com.cts.agentservice.repository.AgentRepository;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AgentService {

    private final AgentRepository agentRepository;
    private final PolicyClient policyClient;
    private final CustomerClient customerClient;
    private final ClaimClient claimClient;

    // **Register a new agent**
    public AgentDTO registerAgent(AgentDTO agentDTO) {
        log.info("Registering new agent: {}", agentDTO.getName());
        Agent agent = new Agent();
        BeanUtils.copyProperties(agentDTO, agent);
        Agent savedAgent = agentRepository.save(agent);

        AgentDTO response = new AgentDTO();
        BeanUtils.copyProperties(savedAgent, response);
        return response;
    }

    // **Get all agents**
    public List<AgentDTO> getAllAgents() {
        log.info("Fetching all agents.");
        return agentRepository.findAll().stream().map(agent -> {
            AgentDTO dto = new AgentDTO();
            BeanUtils.copyProperties(agent, dto);
            return dto;
        }).toList();
    }

    // **Get agent by ID**
    public AgentDTO getAgentById(Long agentId) {
        log.info("Fetching agent details for ID: {}", agentId);
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found."));
        
        AgentDTO response = new AgentDTO();
        BeanUtils.copyProperties(agent, response);
        return response;
    }

    // **Update agent details**
    public AgentDTO updateAgent(Long agentId, AgentDTO agentDTO) {
        log.info("Updating agent ID: {}", agentId);
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found."));

        BeanUtils.copyProperties(agentDTO, agent);
        Agent updatedAgent = agentRepository.save(agent);
        
        AgentDTO response = new AgentDTO();
        BeanUtils.copyProperties(updatedAgent, response);
        return response;
    }

    // **Delete agent**
    public void deleteAgent(Long agentId) {
        log.info("Deleting agent ID: {}", agentId);
        if (!agentRepository.existsById(agentId)) {
            throw new AgentNotFoundException("Agent not found.");
        }
        agentRepository.deleteById(agentId);
    }

    // **Create a new policy**
    public PolicyDTO createPolicy(PolicyDTO policyDTO, Long agentId) {
        log.info("Creating policy for agent ID: {}", agentId);
        Agent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new AgentNotFoundException("Agent not found."));

        policyDTO.setAgentId(agentId);
        return policyClient.createPolicy(policyDTO, agentId);
    }

    // **View assigned policies**
    public List<PolicyDTO> getAssignedPolicies(Long agentId) {
        log.info("Fetching assigned policies for agent ID: {}", agentId);
        return policyClient.getPoliciesByAgent(agentId);
    }

    // **View assigned customers**
    public List<CustomerDTO> getAssignedCustomers(Long agentId) {
        log.info("Fetching assigned customers for agent ID: {}", agentId);
        return customerClient.getCustomersByAgent(agentId);
    }

    // **View assigned claims**
    public List<ClaimDTO> viewClaims(Long agentId) {
        log.info("Fetching assigned claims for agent ID: {}", agentId);
        return claimClient.getClaimsForAgent(agentId);
    }

    // **Verify a claim**
    public void verifyClaim(ClaimVerificationRequest request) {
        log.info("Verifying claim ID: {}", request.getClaimId());
        claimClient.verifyClaim(request);
    }

    // **Calculate total commission earned**
    public CommissionResponse calculateCommission(Long agentId) {
        log.info("Calculating commission for agent ID: {}", agentId);
        List<PolicyDTO> policies = policyClient.getPoliciesByAgent(agentId);

        int policiesSold = policies.size();
        double commissionRate = 0.05;
        double totalCommission = policies.stream()
                .mapToDouble(policy -> policy.getPremium() * commissionRate)
                .sum();

        CommissionResponse response = new CommissionResponse();
        response.setAgentId(agentId);
        response.setPoliciesSold(policiesSold);
        response.setTotalCommissionEarned(totalCommission);

        log.info("Agent ID {} earned total commission of {}", agentId, totalCommission);
        return response;
    }
}
