package com.example.agent.clients;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@FeignClient(name = "policy-service", url = "${policy.service.url}")
public interface PolicyClient {
	  @GetMapping("/policies/agent/{agentId}")
	    List<Map<String, Object>> getPoliciesByAgent(@PathVariable("agentId") Long agentId);

	    @PostMapping("/policies/{policyId}/renew")
	    Map<String, Object> renewPolicy(@PathVariable("policyId") Long policyId);
}
