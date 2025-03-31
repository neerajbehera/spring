package com.example.ecommerce.demo.controller;

import com.example.ecommerce.demo.dto.InventoryRequest;
import com.example.ecommerce.demo.dto.InventoryResponse;
import com.example.ecommerce.demo.model.Inventory;
import com.example.ecommerce.demo.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    // Create or update inventory
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<InventoryResponse> createInventory(@RequestBody List<InventoryRequest> requests) {
        return inventoryService.createInventory(requests);
    }

    // Get all inventory
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventory() {
        return inventoryService.getAllInventory();
    }

    // Get inventory by product ID
    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse getInventoryByProductId(@PathVariable String productId) {
        return inventoryService.getInventoryByProductId(productId);
    }

    // Update inventory
    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse updateInventory(
            @PathVariable String productId,
            @RequestBody InventoryRequest request) {
        return inventoryService.updateInventory(productId, request);
    }

    // Delete inventory
    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteInventory(@PathVariable String productId) {
        inventoryService.deleteInventory(productId);
    }
}