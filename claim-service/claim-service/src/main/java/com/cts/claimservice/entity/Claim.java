package com.cts.claimservice.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "claims")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long claimId;

    @Column(nullable = false)
    private Long policyId;

    @Column(nullable = false)
    private Long customerId;

    private LocalDate dateFiled;
    private String claimReason;
    private Double requestedAmount;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private String accidentLocation;
    private LocalDate accidentDate;
    private String accidentDescription;

    private Long agentId;
    
    private boolean verified;
    private String verificationComments;
}
