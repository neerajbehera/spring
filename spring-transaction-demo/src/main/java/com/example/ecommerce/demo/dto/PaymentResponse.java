package com.example.ecommerce.demo.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.example.ecommerce.demo.model.Payment.PaymentStatus;

@Data
@Builder
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private String transactionId;
    private LocalDateTime paymentDate;
    private BigDecimal amount;
    private String paymentMethod;
    private String message;        // Additional info if needed
    private PaymentStatus paymentStatus;
}