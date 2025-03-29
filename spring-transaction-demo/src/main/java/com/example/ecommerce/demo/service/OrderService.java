package com.example.ecommerce.demo.service;

import com.example.ecommerce.demo.model.Order;
import com.example.ecommerce.demo.model.OrderItem;
import com.example.ecommerce.demo.repository.OrderItemRepository;
import com.example.ecommerce.demo.repository.OrderRepository;
import org.springframework.stereotype.Service;

import com.example.ecommerce.demo.dto.OrderRequest;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class OrderService {
    private final OrderRepository orderRepository = null;
    private final OrderItemRepository orderItemRepository = null;

    @Transactional
    @Autowired
    public Order placeOrder(OrderRequest request) {
        Order order = new Order();
        order.setCustomerId(request.getCustomerId());
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(Order.OrderStatus.PENDING);
        
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

}