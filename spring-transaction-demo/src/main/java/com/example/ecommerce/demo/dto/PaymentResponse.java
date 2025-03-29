package com.example.ecommerce.demo.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private String transactionId;
    private String status;         // "SUCCESS", "FAILED", "PENDING"
    private LocalDateTime paymentDate;
    private Double amount;
    private String paymentMethod;
    private String message;        // Additional info if needed
}