package com.example.jpa.service;

import com.example.jpa.model.AppUser;
import com.example.jpa.repository.UserRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public AppUser saveUser(AppUser user) {
        return userRepository.save(user);
    }

    public Iterable<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

    public AppUser getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<AppUser> findByEmailDomain(String domain) {
        return userRepository.findByEmailDomain(domain);
    }
}