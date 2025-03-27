package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("bankTransferPaymentService")
public class BankTransferPaymentService implements PaymentService {
    @Override
    public String processPayment(double amount) {
        // Simulate bank transfer processing
        return "Bank transfer initiated for $" + amount + " (may take 2-3 business days)";
    }
}