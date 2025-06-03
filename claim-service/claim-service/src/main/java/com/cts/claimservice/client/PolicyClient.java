package com.cts.claimservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.claimservice.dto.PolicyDTO;

@FeignClient(name = "policy-service", url = "http://localhost:8084")
public interface PolicyClient {

    @GetMapping("/api/policies/customer/{customerId}")
    List<PolicyDTO> getCustomerPolicies(@PathVariable("customerId") Long customerId);
}
