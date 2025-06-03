package com.cts.customerservice.exception;

public class RenewalNotAllowedException extends RuntimeException {
	public RenewalNotAllowedException(String message) {
        super(message);
    }

}
