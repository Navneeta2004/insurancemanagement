package com.cts.customerservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cts.customerservice.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	 Optional<Customer> findByEmailAndPhone(String email, String phone);
}
