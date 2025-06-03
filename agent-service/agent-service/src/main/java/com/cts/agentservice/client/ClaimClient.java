package com.cts.agentservice.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cts.agentservice.dto.ClaimDTO;
import com.cts.agentservice.dto.ClaimVerificationRequest;

@FeignClient(name = "claim-service",url="http://localhost:8082")
public interface ClaimClient {

    @GetMapping("/api/claims/agent/{agentId}")
    List<ClaimDTO> getClaimsForAgent(@PathVariable Long agentId);

    @PutMapping("/api/claims/verify")
    void verifyClaim(@RequestBody ClaimVerificationRequest request);
}