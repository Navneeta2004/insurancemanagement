package com.cts.customerservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.customerservice.dto.PolicyDTO;
import com.cts.customerservice.dto.PolicyRenewalRequest;
import com.cts.customerservice.dto.PolicyRenewalResponse;

@FeignClient(name = "policy-service", url = "http://localhost:8084")
public interface PolicyClient {

    @GetMapping("/api/policies/available")
    List<PolicyDTO> getAvailablePolicies();

    @GetMapping("/api/policies/customer/{customerId}")
    List<PolicyDTO> getCustomerPolicies(@PathVariable("customerId") Long customerId);

    @PostMapping("/api/policies/renew")
    PolicyRenewalResponse renewPolicy(@RequestBody PolicyRenewalRequest renewalRequest);
}