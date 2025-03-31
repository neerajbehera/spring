package com.example.ecommerce.demo.repository;

import com.example.ecommerce.demo.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, String> {

    Object findByProductId(String productId);

    boolean existsByProductIdAndQuantityGreaterThanEqual(String productId, Integer quantity);

    int decrementQuantity(String productId, Integer quantity);

    int incrementQuantity(String productId, Integer quantity);
}