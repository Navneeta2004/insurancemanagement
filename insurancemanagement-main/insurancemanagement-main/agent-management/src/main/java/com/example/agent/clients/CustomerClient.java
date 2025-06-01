package com.example.agent.clients;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@FeignClient(name = "customer-service", url = "${customer.service.url}")
public interface CustomerClient {
	 @GetMapping("/customers/agent/{agentId}")
	    List<Map<String, Object>> getCustomersByAgent(@PathVariable("agentId") Long agentId);
	}

