package com.example.ecommerce.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "order_items")  // Explicit table name
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String productId;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}