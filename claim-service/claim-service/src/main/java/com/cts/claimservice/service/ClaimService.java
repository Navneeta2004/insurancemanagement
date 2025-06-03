package com.cts.claimservice.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.cts.claimservice.client.AgentClient;
import com.cts.claimservice.client.CustomerClient;
import com.cts.claimservice.client.PolicyClient;
import com.cts.claimservice.dto.AgentDTO;
import com.cts.claimservice.dto.ClaimDetailsResponse;
import com.cts.claimservice.dto.ClaimRequest;
import com.cts.claimservice.dto.ClaimResponse;
import com.cts.claimservice.dto.ClaimStatusResponse;
import com.cts.claimservice.dto.ClaimVerificationRequest;
import com.cts.claimservice.dto.CustomerDTO;
import com.cts.claimservice.dto.PolicyDTO;
import com.cts.claimservice.entity.Claim;
import com.cts.claimservice.entity.ClaimStatus;
import com.cts.claimservice.exception.AgentNotFoundException;
import com.cts.claimservice.exception.ClaimNotFoundException;
import com.cts.claimservice.exception.PolicyNotFoundException;
import com.cts.claimservice.repository.ClaimRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service
@AllArgsConstructor
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final PolicyClient policyClient;
    private final CustomerClient customerClient;
    private final AgentClient agentClient;

    // **Get all claims**
    public List<ClaimResponse> getAllClaims() {
        log.info("Fetching all claims.");
        return claimRepository.findAll().stream().map(claim -> {
            ClaimResponse response = new ClaimResponse();
            BeanUtils.copyProperties(claim, response);
            return response;
        }).toList();
    }
    
 // **Get detailed claim information**
    public ClaimDetailsResponse getClaimDetails(Long claimId) {
        log.info("Fetching claim details for claimId: {}", claimId);
        
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found."));
        
        ClaimDetailsResponse response = new ClaimDetailsResponse();
        BeanUtils.copyProperties(claim, response);

        log.info("Claim details retrieved for claimId {}", claimId);
        return response;
    }


    // **Get claim details by claim ID**
    public ClaimStatusResponse getClaimById(Long claimId) {
        log.info("Fetching claim status for claimId: {}", claimId);
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found."));

        ClaimStatusResponse response = new ClaimStatusResponse();
        response.setClaimId(claim.getClaimId());
        response.setStatus(claim.getStatus());
        response.setMessage("Claim is currently " + claim.getStatus().name());

        log.info("Claim status for claimId {}: {}", claimId, claim.getStatus());
        return response;
    }

    // **Initiate a claim**
    public ClaimResponse initiateClaim(ClaimRequest claimRequest, Long agentId) {
        log.info("Initiating claim for policyId: {} by customerId: {}", claimRequest.getPolicyId(), claimRequest.getCustomerId());

        // Validate customer existence
        CustomerDTO customer = customerClient.getCustomerById(claimRequest.getCustomerId());
        if (customer == null) {
            throw new PolicyNotFoundException("Customer not found.");
        }

        // Validate policy belongs to customer and is active
        List<PolicyDTO> policies = policyClient.getCustomerPolicies(claimRequest.getCustomerId());
        boolean policyExists = policies.stream()
                .anyMatch(policy -> policy.getPolicyId().equals(claimRequest.getPolicyId()) && policy.getValidityPeriod().isAfter(LocalDate.now()));

        if (!policyExists) {
            throw new PolicyNotFoundException("Policy is either inactive or does not belong to the customer.");
        }

        // Validate agent existence before assigning the claim
        AgentDTO agent = agentClient.getAgentById(agentId);
        if (agent == null) {
            throw new AgentNotFoundException("Agent not found.");
        }

        Claim claim = new Claim();
        BeanUtils.copyProperties(claimRequest, claim);
        claim.setDateFiled(LocalDate.now());
        claim.setStatus(ClaimStatus.FILED);
        claim.setAgentId(agentId);

        Claim savedClaim = claimRepository.save(claim);
        ClaimResponse response = new ClaimResponse();
        BeanUtils.copyProperties(savedClaim, response);

        log.info("Claim filed successfully and assigned to agentId: {}", agentId);
        return response;
    }
    public void verifyClaim(ClaimVerificationRequest request) {
        log.info("Verifying claimId: {}", request.getClaimId());
        Claim claim = claimRepository.findById(request.getClaimId())
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found."));

        claim.setVerified(request.isVerified());
        claim.setVerificationComments(request.getVerificationComments());
        claim.setStatus(request.isVerified() ? ClaimStatus.UNDER_REVIEW : ClaimStatus.REJECTED);

        claimRepository.save(claim);
        log.info("ClaimId {} verified with status: {}", request.getClaimId(), claim.getStatus());
    }

    // **Update an existing claim**
    public ClaimResponse updateClaim(Long claimId, ClaimRequest claimRequest) {
        log.info("Updating claim with ID: {}", claimId);
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new ClaimNotFoundException("Claim not found."));

        BeanUtils.copyProperties(claimRequest, claim);
        Claim updatedClaim = claimRepository.save(claim);

        ClaimResponse response = new ClaimResponse();
        BeanUtils.copyProperties(updatedClaim, response);
        log.info("Claim updated successfully for claimId: {}", claimId);
        return response;
    }

    // **Delete a claim**
    public void deleteClaim(Long claimId) {
        log.info("Deleting claim with ID: {}", claimId);
        if (!claimRepository.existsById(claimId)) {
            throw new ClaimNotFoundException("Claim not found.");
        }
        claimRepository.deleteById(claimId);
        log.info("Claim deleted successfully.");
    }
}

