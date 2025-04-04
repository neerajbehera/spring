package com.example.ecommerce.demo.service;

import com.example.ecommerce.demo.model.Order;
import com.example.ecommerce.demo.model.OrderItem;
import com.example.ecommerce.demo.repository.OrderItemRepository;
import com.example.ecommerce.demo.repository.OrderRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import com.example.ecommerce.demo.dto.OrderItemResponse;
import com.example.ecommerce.demo.dto.OrderRequest;
import com.example.ecommerce.demo.dto.OrderResponse;
import com.example.ecommerce.demo.dto.OrderStatus;
import com.example.ecommerce.demo.dto.OrderUpdateRequest;
import com.example.ecommerce.demo.exception.OrderNotFoundException;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public Order placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        
        // 2. Calculate total amount
        BigDecimal totalAmount = request.getItems().stream()
            .map(item -> BigDecimal.valueOf(item.getUnitPrice())
                .multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setTotalAmount(totalAmount);

        // 3. Save the order first to generate ID
        Order savedOrder = orderRepository.save(order);

        // 4. Create and save OrderItems
        List<OrderItem> orderItems = request.getItems().stream()
            .map(item -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder); // Set the relationship
                orderItem.setProductId(item.getProductId());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setUnitPrice(BigDecimal.valueOf(item.getUnitPrice()));
                return orderItemRepository.save(orderItem);
            })
            .collect(Collectors.toList());

        savedOrder.setItems(orderItems);
        return savedOrder;
        
     
    }

@Transactional(readOnly = true)
public OrderResponse getOrderDetails(Long orderId) {
    Order order = orderRepository.findById(orderId)
        .orElseThrow(() -> new ResponseStatusException(
            HttpStatus.NOT_FOUND, 
            "Order not found with id: " + orderId));

    // Manual mapping
    OrderResponse response = new OrderResponse();
    response.setId(order.getId());
    response.setCustomerId(order.getCustomerId());
    response.setTotalAmount(order.getTotalAmount());
    response.setStatus(order.getStatus());
    response.setOrderDate(order.getOrderDate());
    
    // Map order items
    List<OrderItemResponse> itemResponses = order.getItems().stream()
        .map(item -> {
            OrderItemResponse itemResponse = new OrderItemResponse();
            itemResponse.setId(item.getId());
            itemResponse.setProductId(item.getProductId());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setUnitPrice(item.getUnitPrice());
            return itemResponse;
        })
        .collect(Collectors.toList());
    
    response.setItems(itemResponses);
    
    return response;
}

public Order updateOrder(Long id, OrderUpdateRequest request)  {
    // This will acquire PESSIMISTIC_WRITE lock
        Order order = orderRepository.findByIdWithLock(id)
            .orElseThrow(() -> new OrderNotFoundException("Order not found"));
        System.out.println("updated order status: " + request.getStatus());
        System.out.println("Sleep for 7 seconds to simulate long processing time");
        try {
            Thread.sleep(7000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // Simulate long processing time
        
        // Update order fields
        order.setStatus(request.getStatus());
        order.setTotalAmount(request.getTotalAmount());
        
        Order updatedOrder = orderRepository.save(order);
        System.out.println("Updated order staus : " + updatedOrder.getStatus());
        return updatedOrder;
}

public Order getOrderWithLock(Long id) {
    Order order = orderRepository.findByIdWithLock(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        return order;
}

}