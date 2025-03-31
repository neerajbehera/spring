package com.example.ecommerce.demo.repository;

import com.example.ecommerce.demo.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductId(String productId);
    
    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity - :quantity WHERE i.productId = :productId AND i.quantity >= :quantity")
    int deductInventory(@Param("productId") String productId, @Param("quantity") int quantity);
    
    @Modifying
    @Query("UPDATE Inventory i SET i.quantity = i.quantity + :quantity WHERE i.productId = :productId")
    int addInventory(@Param("productId") String productId, @Param("quantity") int quantity);

    boolean existsByProductIdAndQuantityGreaterThanEqual(String productId, Integer quantity);
}