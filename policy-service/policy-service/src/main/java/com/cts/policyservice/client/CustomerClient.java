package com.cts.policyservice.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cts.policyservice.dto.CustomerDTO;

@FeignClient(name = "customer-service", url = "http://localhost:8083")
public interface CustomerClient {
    @GetMapping("/api/customer/profile/{customerId}")
    CustomerDTO getCustomerById(@PathVariable("customerId") Long customerId);
}
