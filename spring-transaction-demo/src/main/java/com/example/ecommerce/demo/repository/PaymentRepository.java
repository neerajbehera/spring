package com.example.ecommerce.demo.repository;

import com.example.ecommerce.demo.model.Payment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}