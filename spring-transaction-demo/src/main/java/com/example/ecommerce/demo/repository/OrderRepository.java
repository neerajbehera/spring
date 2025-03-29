package com.example.ecommerce.demo.repository;

import com.example.ecommerce.demo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}