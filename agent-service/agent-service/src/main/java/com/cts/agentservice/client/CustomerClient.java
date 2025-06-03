package com.cts.agentservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.agentservice.dto.CustomerDTO;

@FeignClient(name = "customer-service", url = "http://localhost:8083")
public interface CustomerClient {

    @GetMapping("/api/customers/agent/{agentId}")
    List<CustomerDTO> getCustomersByAgent(@PathVariable Long agentId);
}