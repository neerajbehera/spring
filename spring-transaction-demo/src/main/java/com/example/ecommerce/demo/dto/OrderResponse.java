package com.example.ecommerce.demo.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponse {
    private Long id;
    private Long customerId;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private List<OrderItemResponse> items;
    private String shippingAddress;
}