package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("creditCardPaymentService")
public class CreditCardPaymentService implements PaymentService {
    @Override
    public String processPayment(double amount) {
        // Simulate credit card processing
        return "Credit card payment processed for $" + amount;
    }
}