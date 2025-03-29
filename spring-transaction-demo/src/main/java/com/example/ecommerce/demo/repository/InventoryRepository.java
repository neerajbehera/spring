package com.example.ecommerce.demo.repository;

import com.example.ecommerce.demo.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, String> {
}