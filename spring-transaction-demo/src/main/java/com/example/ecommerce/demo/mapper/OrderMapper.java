package com.example.ecommerce.demo.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.example.ecommerce.demo.dto.OrderItemResponse;
import com.example.ecommerce.demo.dto.OrderResponse;
import com.example.ecommerce.demo.model.Order;
import com.example.ecommerce.demo.model.OrderItem;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setCustomerId(order.getCustomerId());
        // ... map other fields
        
        response.setItems(order.getItems().stream()
            .map(this::toItemResponse)
            .collect(Collectors.toList()));
        
        return response;
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        OrderItemResponse itemResponse = new OrderItemResponse();
        itemResponse.setProductId(item.getProductId());
        itemResponse.setQuantity(item.getQuantity());
        // ... map other fields
        return itemResponse;
    }
}