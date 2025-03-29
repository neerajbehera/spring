package com.example.ecommerce.demo.repository;

import com.example.ecommerce.demo.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    // Custom query methods can be added here when needed
    // Example:
    // List<OrderItem> findByOrderId(Long orderId);
}