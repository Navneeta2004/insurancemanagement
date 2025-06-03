package com.cts.customerservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.customerservice.dto.ClaimRequest;
import com.cts.customerservice.dto.ClaimResponse;
import com.cts.customerservice.dto.ClaimStatusResponse;

@FeignClient(name = "claim-service", url = "http://localhost:8082")
public interface ClaimClient {

    @PostMapping("/api/claims")
    ClaimResponse initiateClaim(@RequestBody ClaimRequest claimRequest);

    @GetMapping("/api/claims/{claimId}")
    ClaimStatusResponse getClaimById(@PathVariable("claimId") Long claimId);
}