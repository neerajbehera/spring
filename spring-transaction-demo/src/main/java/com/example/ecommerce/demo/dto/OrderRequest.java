package com.example.ecommerce.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @Valid  // Add this to validate nested objects
    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequest> items;

    // Lombok's @Data already generates these, so they're redundant
    // Can be removed if you're using Lombok consistently
}