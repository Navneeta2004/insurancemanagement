package com.cts.policyservice.dto;

import lombok.Data;

@Data
public class ApplicationFormDTO {
    private Long customerId;
    private Long agentId;
    private Long policyId;
    
    private String vehicleDetails;
    private String registrationNo;
    private String model;
    private String manufacturer;
    private int yearOfPurchase;
    private Double insuredValue;
    private String policyTerm;
    
    private String nomineeName;
    private String nomineeRelation;
}
