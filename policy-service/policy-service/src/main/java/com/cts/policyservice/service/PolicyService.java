package com.cts.policyservice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cts.policyservice.client.AgentClient;
import com.cts.policyservice.client.CustomerClient;
import com.cts.policyservice.dto.AgentDTO;
import com.cts.policyservice.dto.CustomerDTO;
import com.cts.policyservice.dto.PolicyDTO;
import com.cts.policyservice.dto.PolicyRenewalRequest;
import com.cts.policyservice.dto.PolicyRenewalResponse;
import com.cts.policyservice.entity.Policy;
import com.cts.policyservice.exception.AgentNotFoundException;
import com.cts.policyservice.exception.PolicyNotFoundException;
import com.cts.policyservice.exception.RenewalNotAllowedException;
import com.cts.policyservice.repository.PolicyRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class PolicyService {

    private final PolicyRepository policyRepository;
    private final CustomerClient customerClient;
    private final AgentClient agentClient;

    public List<PolicyDTO> getAvailablePolicies() {
        return policyRepository.findAll().stream().map(policy -> {
            PolicyDTO dto = new PolicyDTO();
            BeanUtils.copyProperties(policy, dto);
            return dto;
        }).toList();
    }

    public List<PolicyDTO> getCustomerPolicies(Long customerId) {
        log.info("Fetching policies for customer ID: {}", customerId);

        CustomerDTO customer = customerClient.getCustomerById(customerId);
        if (customer == null) {
            throw new PolicyNotFoundException("Customer not found.");
        }

        return policyRepository.findByCustomerId(customerId).stream().map(policy -> {
            PolicyDTO dto = new PolicyDTO();
            BeanUtils.copyProperties(policy, dto);
            return dto;
        }).toList();
    }

    public Double getAverageInsuredValue(String policyName) {
        return policyRepository.findAverageInsuredValueByPolicyName(policyName);
    }

    public PolicyDTO createPolicy(PolicyDTO request, Long agentId) {
        log.info("Creating new policy: {}", request.getPolicyName());

        AgentDTO agent = agentClient.getAgentById(agentId);
        if (agent == null) {
            throw new AgentNotFoundException("Agent not found.");
        }

        Policy policy = new Policy();
        BeanUtils.copyProperties(request, policy);
        policy.setAgentId(agentId);  // Assigning policy to agent

        Policy savedPolicy = policyRepository.save(policy);
        PolicyDTO responseDTO = new PolicyDTO();
        BeanUtils.copyProperties(savedPolicy, responseDTO);

        log.info("Policy assigned to agent with ID: {}", agentId);
        return responseDTO;
    }

    public PolicyDTO updatePolicy(Long policyId, PolicyDTO request) {
        Policy policy = policyRepository.findById(policyId)
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found."));

        BeanUtils.copyProperties(request, policy);
        Policy updatedPolicy = policyRepository.save(policy);

        PolicyDTO responseDTO = new PolicyDTO();
        BeanUtils.copyProperties(updatedPolicy, responseDTO);
        return responseDTO;
    }

    public void deletePolicy(Long policyId) {
        if (!policyRepository.existsById(policyId)) {
            throw new PolicyNotFoundException("Policy not found.");
        }
        policyRepository.deleteById(policyId);
    }

    public PolicyRenewalResponse renewPolicy(PolicyRenewalRequest renewalRequest) {
        log.info("Processing policy renewal for policyId: {} by customerId: {}", renewalRequest.getPolicyId(), renewalRequest.getCustomerId());

        Policy policy = policyRepository.findById(renewalRequest.getPolicyId())
                .orElseThrow(() -> new PolicyNotFoundException("Policy not found."));

        CustomerDTO customer = customerClient.getCustomerById(renewalRequest.getCustomerId());
        if (customer == null) {
            throw new RenewalNotAllowedException("Customer not found.");
        }

        LocalDate today = LocalDate.now();
        if (policy.getValidityPeriod().isBefore(today) || policy.getValidityPeriod().isAfter(today.plusDays(30))) {
            throw new RenewalNotAllowedException("Policy renewal only allowed within 30 days of expiry.");
        }

        policy.setValidityPeriod(today.plusYears(1));
        policy.setInsuredValue(renewalRequest.getInsuredValue());
        Policy updatedPolicy = policyRepository.save(policy);

        PolicyRenewalResponse response = new PolicyRenewalResponse();
        response.setPolicyId(updatedPolicy.getPolicyid());
        response.setStatus("SUCCESS");
        response.setMessage("Policy renewed successfully with updated insured value.");
        log.info("Policy renewed successfully for policyId: {}", renewalRequest.getPolicyId());
        return response;
    }
}
