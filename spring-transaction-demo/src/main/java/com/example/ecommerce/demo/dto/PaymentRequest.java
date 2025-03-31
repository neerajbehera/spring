package com.example.ecommerce.demo.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PaymentRequest {
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;  // "CREDIT_CARD", "PAYPAL", etc.

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Card number is required for card payments")
    private String cardNumber;     // Only required for card payments
    
    private String cardHolderName;
    private String expiryDate;
    private String cvv;
    
    @NotBlank(message = "Currency is required")
    @Size(min = 3, max = 3, message = "Currency must be 3 characters")
    private String currency;       // "USD", "EUR", etc.

    // For non-card payments (like PayPal)
    private String paymentToken;
}