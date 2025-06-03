package com.cts.customerservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cts.customerservice.client.ClaimClient;
import com.cts.customerservice.client.PolicyClient;
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
import com.cts.customerservice.entity.Customer;
import com.cts.customerservice.exception.CustomerNotFoundException;
import com.cts.customerservice.exception.RenewalNotAllowedException;
import com.cts.customerservice.repository.CustomerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PolicyClient policyClient;
    private final ClaimClient claimClient;

    public CustomerService(CustomerRepository customerRepository,
                           PolicyClient policyClient,
                           ClaimClient claimClient) {
        this.customerRepository = customerRepository;
        this.policyClient = policyClient;
        this.claimClient = claimClient;
    }

    public CustomerResponseDTO registerCustomer(RegisterRequest request) {
        log.info("Registering customer with email: {}", request.getEmail());
        Customer customer = new Customer();
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setName(request.getName());
        customer.setAddress(request.getAddress());
        Customer saved = customerRepository.save(customer);

        CustomerResponseDTO response = new CustomerResponseDTO();
        BeanUtils.copyProperties(saved, response);
        log.info("Customer registered with id: {}", response.getId());
        return response;
    }

    public CustomerResponseDTO loginCustomer(LoginRequest request) {
        log.info("Customer login attempt for email: {} and phone: {}", request.getEmail(), request.getPhone());
        Optional<Customer> optional = customerRepository.findByEmailAndPhone(request.getEmail(), request.getPhone());
        if (!optional.isPresent()) {
            log.error("Customer not found with email: {} and phone: {}", request.getEmail(), request.getPhone());
            throw new CustomerNotFoundException("Invalid login credentials.");
        }
        Customer customer = optional.get();
        CustomerResponseDTO response = new CustomerResponseDTO();
        BeanUtils.copyProperties(customer, response);
        log.info("Customer login successful for id: {}", response.getId());
        return response;
    }

    public CustomerResponseDTO getCustomerProfile(Long customerId) {
        log.info("Fetching profile for customerId: {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
        CustomerResponseDTO response = new CustomerResponseDTO();
        BeanUtils.copyProperties(customer, response);
        return response;
    }

    public CustomerResponseDTO updateCustomerProfile(Long customerId, CustomerUpdateRequest updateRequest) {
        log.info("Updating profile for customerId: {}", customerId);
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found."));
        if (updateRequest.getName() != null) {
            customer.setName(updateRequest.getName());
        }
        if (updateRequest.getAddress() != null) {
            customer.setAddress(updateRequest.getAddress());
        }
        Customer updated = customerRepository.save(customer);
        CustomerResponseDTO response = new CustomerResponseDTO();
        BeanUtils.copyProperties(updated, response);
        log.info("Customer profile updated for id: {}", response.getId());
        return response;
    }

    public void deleteCustomerProfile(Long customerId) {
        log.info("Deleting profile for customerId: {}", customerId);
        if(!customerRepository.existsById(customerId)) {
            log.error("Customer not found for deletion with id: {}", customerId);
            throw new CustomerNotFoundException("Customer not found.");
        }
        customerRepository.deleteById(customerId);
        log.info("Customer profile deleted for id: {}", customerId);
    }

    public List<PolicyDTO> getAvailablePolicies() {
        log.info("Fetching available policies from policy service.");
        List<PolicyDTO> policies = policyClient.getAvailablePolicies();
        log.info("Received {} available policies.", policies.size());
        return policies;
    }

    public List<PolicyDTO> getCustomerPolicies(Long customerId) {
        log.info("Fetching policies for customer with id: {}", customerId);
        List<PolicyDTO> policies = policyClient.getCustomerPolicies(customerId);
        log.info("Received {} policies for customer id: {}", policies.size(), customerId);
        return policies;
    }

    public PolicyRenewalResponse renewPolicy(PolicyRenewalRequest renewalRequest) {
        log.info("Processing policy renewal for customerId: {} and policyId: {}",
                renewalRequest.getCustomerId(), renewalRequest.getPolicyId());
        PolicyRenewalResponse response = policyClient.renewPolicy(renewalRequest);
        if (response == null || !"SUCCESS".equalsIgnoreCase(response.getStatus())) {
            log.error("Policy renewal failed for policyId: {}", renewalRequest.getPolicyId());
            throw new RenewalNotAllowedException("Policy renewal not allowed or failed.");
        }
        log.info("Policy renewal successful for policyId: {}", renewalRequest.getPolicyId());
        return response;
    }

    public ClaimResponse initiateClaim(ClaimRequest claimRequest) {
        log.info("Initiating claim for customerId: {} and policyId: {}",
                claimRequest.getCustomerId(), claimRequest.getPolicyId());
        ClaimResponse claimResponse = claimClient.initiateClaim(claimRequest);
        log.info("Claim initiated with id: {}", claimResponse.getClaimId());
        return claimResponse;
    }

    public ClaimStatusResponse getClaimStatus(Long claimId) {
        log.info("Fetching claim status for claimId: {}", claimId);
        ClaimStatusResponse statusResponse = claimClient.getClaimById(claimId);
        log.info("Claim status for claimId {} is {}", claimId, statusResponse.getStatus());
        return statusResponse;
    }
}
