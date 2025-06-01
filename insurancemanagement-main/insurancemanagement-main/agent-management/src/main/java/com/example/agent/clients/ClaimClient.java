package com.example.agent.clients;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "claim-service", url = "${claim.service.url}")
public interface ClaimClient {
	 @GetMapping("/claims/agent/{agentId}")
	    List<Map<String, Object>> getClaimsByAgent(@PathVariable("agentId") Long agentId);

	    @PostMapping("/claims/{claimId}/process")
	    Map<String, Object> processClaim(@PathVariable("claimId") Long claimId);
} 
