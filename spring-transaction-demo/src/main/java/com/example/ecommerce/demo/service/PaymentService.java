package com.example.ecommerce.demo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.demo.dto.OrderStatus;
import com.example.ecommerce.demo.dto.PaymentRequest;
import com.example.ecommerce.demo.dto.PaymentResponse;
import com.example.ecommerce.demo.exception.OrderNotFoundException;
import com.example.ecommerce.demo.exception.PaymentProcessingException;
import com.example.ecommerce.demo.model.Order;
import com.example.ecommerce.demo.model.Payment;
import com.example.ecommerce.demo.model.Payment.PaymentStatus;
import com.example.ecommerce.demo.repository.OrderRepository;
import com.example.ecommerce.demo.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentResponse processPayment(Long orderId, PaymentRequest paymentRequest) {
        // Validate payment request amount
        if (paymentRequest.getAmount() == null || paymentRequest.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new PaymentProcessingException("Payment amount must be positive");
        }

        // Fetch the order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        // Create and save payment
        Payment payment = createPayment(orderId, paymentRequest);
        Payment savedPayment = paymentRepository.save(payment);

        // Update order status
        order.setStatus(OrderStatus.PROCESSING);
        orderRepository.save(order);

        // Convert Payment to PaymentResponse manually
        return convertToPaymentResponse(savedPayment);
    }

    private Payment createPayment(Long orderId, PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(orderId)
                .amount(paymentRequest.getAmount())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .status(PaymentStatus.COMPLETED)
                .build();
    }

    private PaymentResponse convertToPaymentResponse(Payment payment) {
        return PaymentResponse.builder()
                .paymentId(payment.getId())
                .orderId(payment.getOrderId())
                .amount(payment.getAmount())
                .paymentStatus(payment.getStatus())
                .paymentMethod(payment.getPaymentMethod())
                .build();
    }
}