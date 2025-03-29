package com.example.ecommerce.demo.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Inventory {
    @Id
    private String productId;
    
    @Column(nullable = false)
    private Integer quantity;
    
    @Version
    private Long version; // For optimistic locking
}