package com.cts.customerservice.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.cts.customerservice.dto.ClaimRequest;
import com.cts.customerservice.dto.ClaimResponse;
import com.cts.customerservice.dto.ClaimStatusResponse;
import com.cts.customerservice.dto.CustomerResponseDTO;
import com.cts.customerservice.dto.CustomerUpdateRequest;
import com.cts.customerservice.dto.LoginRequest;
import com.cts.customerservice.dto.PolicyDTO;
import com.cts.customerservice.dto.PolicyRenewalRequest;
import com.cts.customerservice.dto.PolicyRenewalResponse;
import com.cts.customerservice.dto.RegisterRequest;
import com.cts.customerservice.service.CustomerService;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // Registration endpoint
    @PostMapping("/register")
    public CustomerResponseDTO registerCustomer(@RequestBody RegisterRequest request) {
        return customerService.registerCustomer(request);
    }

    // Login endpoint (authentication is done at the API gateway)
    @PostMapping("/login")
    public CustomerResponseDTO loginCustomer(@RequestBody LoginRequest request) {
        return customerService.loginCustomer(request);
    }

    // Get customer profile
    @GetMapping("/profile/{customerId}")
    public CustomerResponseDTO getProfile(@PathVariable Long customerId) {
        return customerService.getCustomerProfile(customerId);
    }

    // Update customer profile
    @PutMapping("/profile/{customerId}")
    public CustomerResponseDTO updateProfile(@PathVariable Long customerId,
                                             @RequestBody CustomerUpdateRequest updateRequest) {
        return customerService.updateCustomerProfile(customerId, updateRequest);
    }

    // Delete customer profile
    @DeleteMapping("/profile/{customerId}")
    public void deleteProfile(@PathVariable Long customerId) {
        customerService.deleteCustomerProfile(customerId);
    }

    // Get available policies (from Policy Module via Feign client)
    @GetMapping("/policies/available")
    public List<PolicyDTO> getAvailablePolicies() {
        return customerService.getAvailablePolicies();
    }

    // Get policies belonging to the customer (from Policy Module)
    @GetMapping("/policies/my")
    public List<PolicyDTO> getMyPolicies(@RequestParam Long customerId) {
        return customerService.getCustomerPolicies(customerId);
    }

    // Renew policy request (renewal will be activated only before 30 days of expiry as checked by the policy module)
    @PostMapping("/policies/renew")
    public PolicyRenewalResponse renewPolicy(@RequestBody PolicyRenewalRequest renewalRequest) {
        return customerService.renewPolicy(renewalRequest);
    }

    // Initiate a claim for a policy (via Claim Module)
    @PostMapping("/claims/initiate")
    public ClaimResponse initiateClaim(@RequestBody ClaimRequest claimRequest) {
        return customerService.initiateClaim(claimRequest);
    }

    // Get claim status from the Claim Module
    @GetMapping("/claims/status/{claimId}")
    public ClaimStatusResponse getClaimStatus(@PathVariable Long claimId) {
        return customerService.getClaimStatus(claimId);
    }
}

