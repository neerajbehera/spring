package com.example.ecommerce.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private Long orderId;
    
    @Column(nullable = false)
    private Double amount;
    
    private String transactionId;
    
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
    
    private LocalDateTime paymentDate;
    
    public enum PaymentStatus {
        PENDING, COMPLETED, FAILED, REFUNDED
    }
    
    @PrePersist
    protected void onCreate() {
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    public void setPaymentMethod(String paymentMethod) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPaymentMethod'");
    }
}