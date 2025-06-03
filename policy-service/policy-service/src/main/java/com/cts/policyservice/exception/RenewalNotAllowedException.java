package com.cts.policyservice.exception;

public class RenewalNotAllowedException extends RuntimeException {
    public RenewalNotAllowedException(String message) {
        super(message);
    }
}
