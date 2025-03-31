package com.example.ecommerce.demo.service;

import com.example.ecommerce.demo.exception.InventoryException;
import com.example.ecommerce.demo.model.OrderItem;
import com.example.ecommerce.demo.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public void checkInventory(List<OrderItem> items) {
        items.forEach(item -> {
            boolean inStock = inventoryRepository.existsByProductIdAndQuantityGreaterThanEqual(
                item.getProductId(),
                item.getQuantity()
            );
            
            if (!inStock) {
                throw new InventoryException("Insufficient stock for product: " + item.getProductId());
            }
        });
    }

    @Transactional
    public void updateInventory(List<OrderItem> items, InventoryUpdateType updateType) {
        items.forEach(item -> {
            int updatedRows = updateType == InventoryUpdateType.DECREMENT
                ? inventoryRepository.decrementQuantity(item.getProductId(), item.getQuantity())
                : inventoryRepository.incrementQuantity(item.getProductId(), item.getQuantity());
            
            if (updatedRows == 0) {
                throw new InventoryException("Inventory update failed for product: " + item.getProductId());
            }
        });
    }

    public enum InventoryUpdateType {
        INCREMENT, DECREMENT
    }

    public Object findByProductId(String productId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByProductId'");
    }
}