package com.cts.agentservice.controller;



import com.cts.agentservice.dto.*;
import com.cts.agentservice.service.AgentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;

    // **Register a new agent**
    @PostMapping("/register")
    public AgentDTO registerAgent(@RequestBody AgentDTO agentDTO) {
        return agentService.registerAgent(agentDTO);
    }

    // **Get all agents**
    @GetMapping
    public List<AgentDTO> getAllAgents() {
        return agentService.getAllAgents();
    }

    // **Get agent details by ID**
    @GetMapping("/{agentId}")
    public AgentDTO getAgentById(@PathVariable Long agentId) {
        return agentService.getAgentById(agentId);
    }

    // **Update agent details**
    @PutMapping("/{agentId}")
    public AgentDTO updateAgent(@PathVariable Long agentId, @RequestBody AgentDTO agentDTO) {
        return agentService.updateAgent(agentId, agentDTO);
    }

    // **Delete agent**
    @DeleteMapping("/{agentId}")
    public void deleteAgent(@PathVariable Long agentId) {
        agentService.deleteAgent(agentId);
    }

    // **Create a new policy for customer**
    @PostMapping("/{agentId}/policy")
    public PolicyDTO createPolicy(@RequestBody PolicyDTO policyDTO, @PathVariable Long agentId) {
        return agentService.createPolicy(policyDTO, agentId);
    }

    // **View assigned policies**
    @GetMapping("/{agentId}/policies")
    public List<PolicyDTO> getAssignedPolicies(@PathVariable Long agentId) {
        return agentService.getAssignedPolicies(agentId);
    }

    // **View assigned customers**
    @GetMapping("/{agentId}/customers")
    public List<CustomerDTO> getAssignedCustomers(@PathVariable Long agentId) {
        return agentService.getAssignedCustomers(agentId);
    }

    // **View assigned claims**
    @GetMapping("/{agentId}/claims")
    public List<ClaimDTO> viewClaims(@PathVariable Long agentId) {
        return agentService.viewClaims(agentId);
    }

    // **Verify a claim**
    @PutMapping("/verify-claim")
    public void verifyClaim(@RequestBody ClaimVerificationRequest request) {
        agentService.verifyClaim(request);
    }

    // **Calculate total commission earned**
    @GetMapping("/{agentId}/commission")
    public CommissionResponse calculateCommission(@PathVariable Long agentId) {
        return agentService.calculateCommission(agentId);
    }
}
