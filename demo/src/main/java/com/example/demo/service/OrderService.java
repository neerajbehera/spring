package com.example.demo.service;

import com.example.demo.model.Order;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final PaymentService paymentService;
    private final NotificationService notificationService;


    public OrderService(@Qualifier("creditCardPaymentService") PaymentService paymentService,@Qualifier("emailNotificationService") NotificationService notificationService) {
        this.paymentService = paymentService;
        this.notificationService = notificationService;
        System.out.println("OrderService constructor called");
    }

    public String placeOrder(Order order) {
        // Process payment
        String paymentResult = paymentService.processPayment(order.getAmount());
        
        // Send notification
        notificationService.sendConfirmation(
            order.getCustomerEmail(),
            "Your order " + order.getOrderId() + " has been placed. " + paymentResult
        );
        
        return "Order placed successfully. " + paymentResult;
    }
}