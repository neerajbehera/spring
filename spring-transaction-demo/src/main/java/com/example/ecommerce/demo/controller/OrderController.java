package com.example.ecommerce.demo.controller;

import com.example.ecommerce.demo.dto.OrderRequest;
import com.example.ecommerce.demo.dto.OrderResponse;
import com.example.ecommerce.demo.dto.OrderStatus;
import com.example.ecommerce.demo.dto.PaymentRequest;
import com.example.ecommerce.demo.model.*;
import com.example.ecommerce.demo.repository.OrderRepository;
import com.example.ecommerce.demo.repository.PaymentRepository;
import com.example.ecommerce.demo.service.OrderService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderRepository orderRepository;
    private PaymentRepository paymentRepository;
    private OrderService orderService;

    public OrderController(OrderRepository orderRepository,
                         PaymentRepository paymentRepository,
                         OrderService orderService) {
        this.orderRepository = orderRepository;
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }

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
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);
        System.out.println("saveorder processed: ");
        return ResponseEntity.ok(savedOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderDetails(
            @PathVariable Long orderId) {
        
        OrderResponse response = orderService.getOrderDetails(orderId);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/{orderId}/payments")
    public ResponseEntity<Payment> processPayment(
            @PathVariable Long orderId,
            @RequestBody PaymentRequest paymentRequest) {
        
        System.out.println("Order ID: " + orderId);
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
        order.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);  // Save the order with updated status
        
        return ResponseEntity.ok(savedPayment);
    }
}