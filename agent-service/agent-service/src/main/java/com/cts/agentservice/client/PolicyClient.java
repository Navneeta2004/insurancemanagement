package com.cts.agentservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.agentservice.dto.PolicyDTO;

@FeignClient(name = "policy-service", url = "http://localhost:8084")
public interface PolicyClient {

    @GetMapping("/api/policies/agent/{agentId}")
    List<PolicyDTO> getPoliciesByAgent(@PathVariable("agentId") Long agentId);

    @PostMapping("/api/policies/{agentId}")
    PolicyDTO createPolicy(@RequestBody PolicyDTO policyDTO, @PathVariable Long agentId);
}