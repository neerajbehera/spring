package com.example.ecommerce.demo.controller;

import com.example.ecommerce.demo.dto.OrderRequest;
import com.example.ecommerce.demo.dto.PaymentRequest;
import com.example.ecommerce.demo.model.*;
import com.example.ecommerce.demo.repository.OrderRepository;
import com.example.ecommerce.demo.repository.PaymentRepository;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    
    @Autowired
    private PaymentRepository paymentRepository;


    @GetMapping("/test")
    public String test() {
        return "API is working";
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest request) {
        System.out.println("Creating order...");
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        // Convert OrderItemRequest to OrderItem entities
        List<OrderItem> orderItems = request.getItems().stream()
            .map(item -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(order); // Set the relationship
                orderItem.setProductId(item.getProductId());
                orderItem.setQuantity(item.getQuantity());
                orderItem.setUnitPrice(BigDecimal.valueOf(item.getUnitPrice()));
                return orderItem;
            })
            .collect(Collectors.toList());

        System.out.println("Order items processed: ");
        
        order.setItems(orderItems);
        
        // Calculate total using BigDecimal
        BigDecimal totalAmount = request.getItems().stream()
            .map(item -> BigDecimal.valueOf(item.getUnitPrice())
                .multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("Total processed: ");
        
        order.setTotalAmount(totalAmount);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        System.out.println("saveorder processed: ");
        return ResponseEntity.ok(savedOrder);
    }

    @PostMapping("/{orderId}/payments")
    public ResponseEntity<Payment> processPayment(
            @PathVariable Long orderId,
            @RequestBody PaymentRequest paymentRequest) {
        
        // First get the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        
        // Create and save payment
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setAmount(paymentRequest.getAmount());
        payment.setPaymentMethod(paymentRequest.getPaymentMethod());
        payment.setStatus(Payment.PaymentStatus.COMPLETED);
        
        Payment savedPayment = paymentRepository.save(payment);  // Use paymentRepository here
        
        // Update order status
        order.setStatus(Order.OrderStatus.PROCESSING);
        orderRepository.save(order);  // Save the order with updated status
        
        return ResponseEntity.ok(savedPayment);
    }
}