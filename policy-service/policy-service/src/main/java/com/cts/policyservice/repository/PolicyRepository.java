package com.cts.policyservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.cts.policyservice.entity.Policy;

public interface PolicyRepository extends JpaRepository<Policy, Long> {
	List<Policy> findByCustomerId(Long customerId);

    @Query("SELECT AVG(p.insuredValue) FROM Policy p WHERE p.policyName = :policyName")
    Double findAverageInsuredValueByPolicyName(String policyName);

}
