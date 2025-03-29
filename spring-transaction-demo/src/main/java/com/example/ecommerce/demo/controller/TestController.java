package com.example.ecommerce.demo.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
public class TestController {
    
    @GetMapping("/check")
    public String healthCheck() {
        return "API is working at " + System.currentTimeMillis();
    }
}