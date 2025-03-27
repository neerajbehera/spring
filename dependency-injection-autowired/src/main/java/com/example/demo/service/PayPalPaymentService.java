package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("paypalPaymentService")
public class PayPalPaymentService implements PaymentService {
    @Override
    public String processPayment(double amount) {
        // Simulate PayPal processing
        return "PayPal payment processed for $" + amount + " (including $0.30 + 2.9% fee)";
    }
}