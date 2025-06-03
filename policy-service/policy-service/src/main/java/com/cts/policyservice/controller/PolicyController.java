package com.cts.policyservice.controller;



import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.policyservice.dto.PolicyDTO;
import com.cts.policyservice.dto.PolicyRenewalRequest;
import com.cts.policyservice.dto.PolicyRenewalResponse;
import com.cts.policyservice.service.PolicyService;

import lombok.RequiredArgsConstructor;


import java.util.List;

@RestController
@RequestMapping("/api/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @GetMapping("/available")
    public List<PolicyDTO> getAvailablePolicies() {
        return policyService.getAvailablePolicies();
    }

    @GetMapping("/customer/{customerId}")
    public List<PolicyDTO> getCustomerPolicies(@PathVariable Long customerId) {
        return policyService.getCustomerPolicies(customerId);
    }

    @GetMapping("/average-insured/{policyName}")
    public Double getAverageInsuredValue(@PathVariable String policyName) {
        return policyService.getAverageInsuredValue(policyName);
    }

    @PostMapping("/{agentId}")
    public PolicyDTO createPolicy(@RequestBody PolicyDTO request, @PathVariable Long agentId) {
        return policyService.createPolicy(request, agentId);
    }

    @PutMapping("/{policyId}")
    public PolicyDTO updatePolicy(@PathVariable Long policyId, @RequestBody PolicyDTO request) {
        return policyService.updatePolicy(policyId, request);
    }

    @DeleteMapping("/{policyId}")
    public void deletePolicy(@PathVariable Long policyId) {
        policyService.deletePolicy(policyId);
    }

    @PostMapping("/renew")
    public PolicyRenewalResponse renewPolicy(@RequestBody PolicyRenewalRequest renewalRequest) {
        return policyService.renewPolicy(renewalRequest);
    }
}
