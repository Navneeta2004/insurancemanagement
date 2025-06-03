package com.cts.claimservice.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cts.claimservice.dto.ClaimDetailsResponse;
import com.cts.claimservice.dto.ClaimRequest;
import com.cts.claimservice.dto.ClaimResponse;
import com.cts.claimservice.dto.ClaimStatusResponse;
import com.cts.claimservice.dto.ClaimVerificationRequest;
import com.cts.claimservice.service.ClaimService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {

    private final ClaimService claimService;

    // **Initiate a new claim**
    @PostMapping("/{agentId}/initiate")
    public ClaimResponse initiateClaim(@RequestBody ClaimRequest claimRequest, @PathVariable Long agentId) {
        return claimService.initiateClaim(claimRequest, agentId);
    }

    // **Get claim status by claim ID**
    @GetMapping("/{claimId}")
    public ClaimStatusResponse getClaimById(@PathVariable Long claimId) {
        return claimService.getClaimById(claimId);
    }

    // **Get detailed claim information**
    @GetMapping("/{claimId}/details")
    public ClaimDetailsResponse getClaimDetails(@PathVariable Long claimId) {
        return claimService.getClaimDetails(claimId);
    }

    // **Verify a claim**
    @PutMapping("/verify")
    public void verifyClaim(@RequestBody ClaimVerificationRequest request) {
        claimService.verifyClaim(request);
    }

    // **Get all claims**
    @GetMapping
    public List<ClaimResponse> getAllClaims() {
        return claimService.getAllClaims();
    }

    // **Update an existing claim**
    @PutMapping("/{claimId}")
    public ClaimResponse updateClaim(@PathVariable Long claimId, @RequestBody ClaimRequest claimRequest) {
        return claimService.updateClaim(claimId, claimRequest);
    }

    // **Delete a claim**
    @DeleteMapping("/{claimId}")
    public void deleteClaim(@PathVariable Long claimId) {
        claimService.deleteClaim(claimId);
    }
}