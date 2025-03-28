package com.example.jpa.controller;

import com.example.jpa.model.AppUser;
import com.example.jpa.service.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public AppUser createUser(@RequestBody AppUser user) {
        return userService.saveUser(user);
    }

    @GetMapping
    public Iterable<AppUser> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public AppUser getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
    }

    // 1. Get users by email domain (native query)
    @GetMapping("/by-domain")
    public List<AppUser> getUsersByDomain(@RequestParam String domain) {
        return userService.findByEmailDomain(domain);
    }
}