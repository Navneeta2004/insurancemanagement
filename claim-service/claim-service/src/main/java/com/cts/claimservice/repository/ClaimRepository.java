package com.cts.claimservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.claimservice.entity.Claim;

public interface ClaimRepository extends JpaRepository<Claim, Long> {
    List<Claim> findByCustomerId(Long customerId);
    List<Claim> findByPolicyId(Long policyId);
    List<Claim> findByAgentId(Long agentId);
}

