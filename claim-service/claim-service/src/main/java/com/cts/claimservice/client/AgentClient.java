package com.cts.claimservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.claimservice.dto.AgentDTO;

@FeignClient(name = "agent-service", url = "http://localhost:8081")
public interface AgentClient {

    @GetMapping("/api/agents/{agentId}")
    AgentDTO getAgentById(@PathVariable("agentId") Long agentId);
}