package com.example.agent.service;

import org.springframework.stereotype.Service;

@Service
public class CommissionService {
    private static final double BASE_COMMISSION = 500.0;
    private static final double POLICY_COMMISSION_RATE = 50.0;

    public double calculateCommission(int policyCount) {
        return BASE_COMMISSION + (policyCount * POLICY_COMMISSION_RATE);
    }
}
