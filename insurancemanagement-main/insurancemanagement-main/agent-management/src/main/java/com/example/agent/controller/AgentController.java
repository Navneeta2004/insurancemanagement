package com.example.agent.controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.agent.dto.AgentRequestDTO;
import com.example.agent.dto.AgentResponseDTO;
import com.example.agent.entity.Agent;
import com.example.agent.service.AgentService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/agents")
@RequiredArgsConstructor
@Slf4j
public class AgentController {

    private final AgentService agentService;

    @PostMapping
    public ResponseEntity<Agent> createAgent(@RequestBody AgentRequestDTO dto) {
        return ResponseEntity.ok(agentService.createAgent(dto));
    }

    @GetMapping("/{agentId}")
    public ResponseEntity<AgentResponseDTO> getAgentDetails(@PathVariable Long agentId) {
        return ResponseEntity.ok(agentService.getAgentDetails(agentId));
    }

    @PutMapping("/{agentId}")
    public ResponseEntity<Agent> updateAgent(@PathVariable Long agentId, @RequestBody AgentRequestDTO dto) {
        return ResponseEntity.ok(agentService.updateAgent(agentId, dto));
    }

    @DeleteMapping("/{agentId}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long agentId) {
        agentService.deleteAgent(agentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{agentId}/customers")
    public ResponseEntity<List<Map<String, Object>>> getCustomersByAgent(@PathVariable Long agentId) {
        return ResponseEntity.ok(agentService.getCustomersByAgent(agentId));
    }

    @PostMapping("/{agentId}/renew-policy/{policyId}")
    public ResponseEntity<Map<String, Object>> renewPolicy(@PathVariable Long agentId, @PathVariable Long policyId) {
        return ResponseEntity.ok(agentService.renewPolicy(agentId, policyId));
    }

    @PostMapping("/{agentId}/process-claim/{claimId}")
    public ResponseEntity<Map<String, Object>> processClaim(@PathVariable Long agentId, @PathVariable Long claimId) {
        return ResponseEntity.ok(agentService.processClaim(agentId, claimId));
    }
}
