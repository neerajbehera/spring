package com.example.ecommerce.demo.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderUpdateRequest {
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String customerNotes;
    private List<OrderItemUpdateRequest> items;

    // Constructors, getters, and setters
    public OrderUpdateRequest() {
    }

    public OrderUpdateRequest(OrderStatus status, BigDecimal totalAmount, String customerNotes) {
        this.status = status;
        this.totalAmount = totalAmount;
        this.customerNotes = customerNotes;
    }

}
