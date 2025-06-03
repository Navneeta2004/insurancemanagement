package com.cts.policyservice.entity;


import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "policies")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long policyid;

    @Column(nullable = false, unique = true)
    private String policyName;

    @Column(nullable = false)
    private Double premium;

    private String renewal;
    private String coverageDetails;
    private LocalDate validityPeriod;
    
    @Column(nullable = false)
    private Double insuredValue;
    
    @Column(nullable = false)
    private Long customerId;
    

    private Long agentId; 
}
