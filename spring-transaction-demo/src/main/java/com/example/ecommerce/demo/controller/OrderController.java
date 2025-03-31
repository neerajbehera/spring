package com.example.ecommerce.demo.controller;

import com.example.ecommerce.demo.dto.OrderRequest;
import com.example.ecommerce.demo.dto.OrderResponse;
import com.example.ecommerce.demo.dto.PaymentRequest;
import com.example.ecommerce.demo.dto.PaymentResponse;
import com.example.ecommerce.demo.model.*;
import com.example.ecommerce.demo.service.InventoryService;
import com.example.ecommerce.demo.service.OrderService;
import com.example.ecommerce.demo.service.PaymentService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    private PaymentService paymentService;
    private InventoryService inventoryService;
    
        public OrderController(OrderService orderService,PaymentService paymentService, InventoryService inventoryService) {
            this.orderService = orderService;
            this.paymentService = paymentService;
            this.inventoryService = inventoryService;
    }

    @GetMapping("/test")
    public String test() {
        return "API is working";
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@Valid @RequestBody OrderRequest request) {
        System.out.println("Creating order...");
        // 1. Check inventory
        inventoryService.checkAndDeductInventory(request.getItems());
        Order order = orderService.placeOrder(request);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(
            @PathVariable Long orderId) {
        
        OrderResponse response = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{orderId}/payments")
    public ResponseEntity<PaymentResponse> processPayment(
            @PathVariable Long orderId,
            @RequestBody PaymentRequest paymentRequest) {
        
            PaymentResponse response = paymentService.processPayment(orderId, paymentRequest);
            return ResponseEntity.ok(response);

    }

   
}