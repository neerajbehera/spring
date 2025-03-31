package com.example.ecommerce.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.ecommerce.demo.dto.InventoryRequest;
import com.example.ecommerce.demo.dto.InventoryResponse;
import com.example.ecommerce.demo.dto.OrderItemRequest;
import com.example.ecommerce.demo.exception.InventoryException;
import com.example.ecommerce.demo.model.Inventory;
import com.example.ecommerce.demo.repository.InventoryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional
    public void checkAndDeductInventory(List<OrderItemRequest> items) {
        // First pass - check all items are available
        items.forEach(item -> {
            boolean available = inventoryRepository.existsByProductIdAndQuantityGreaterThanEqual(
                item.getProductId(),
                item.getQuantity()
            );
            if (!available) {
                throw new InventoryException("Insufficient stock for product: " + item.getProductId());
            }
        });

        // Second pass - perform atomic deductions
        items.forEach(item -> {
            int updatedRows = inventoryRepository.deductInventory(
                item.getProductId(),
                item.getQuantity()
            );
            if (updatedRows == 0) {
                throw new InventoryException("Concurrent modification detected for product: " + item.getProductId());
            }
        });
    }

    @Transactional
    public List<InventoryResponse> createInventory(List<InventoryRequest> requests) {
        return requests.stream()
                .map(request -> {
                    Inventory inventory = Inventory.builder()
                            .productId(request.getProductId())
                            .productName(request.getProductName())
                            .quantity(request.getQuantity())
                            .build();
                    inventory = inventoryRepository.save(inventory);
                    return mapToResponse(inventory);
                })
                .collect(Collectors.toList());
    }

    public List<InventoryResponse> getAllInventory() {
        return inventoryRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public InventoryResponse getInventoryByProductId(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryException("Inventory not found for product: " + productId));
        return mapToResponse(inventory);
    }

    @Transactional
    public InventoryResponse updateInventory(String productId, InventoryRequest request) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryException("Inventory not found for product: " + productId));

        inventory.setProductName(request.getProductName());
        inventory.setQuantity(request.getQuantity());
        inventory = inventoryRepository.save(inventory);

        return mapToResponse(inventory);
    }

    @Transactional
    public void deleteInventory(String productId) {
        Inventory inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new InventoryException("Inventory not found for product: " + productId));
        inventoryRepository.delete(inventory);
    }

    private InventoryResponse mapToResponse(Inventory inventory) {
        return InventoryResponse.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .productName(inventory.getProductName())
                .quantity(inventory.getQuantity())
                .build();
    }
}