package com.tracw.tracw.service;

import com.tracw.tracw.model.User;
import com.tracw.tracw.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(String username, String rawPassword, String phoneNumber, BigDecimal initialBalance) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.findByPhoneNumber(phoneNumber).isPresent()) {
            throw new RuntimeException("Phone number already registered");
        }

        // Validate phone number format
        if (!isValidPhoneNumber(phoneNumber)) {
            throw new RuntimeException("Invalid phone number format");
        }

        User user = new User(username, passwordEncoder.encode(rawPassword), phoneNumber, initialBalance);
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Add your phone number validation logic here
        // This is a simple example - you might want more sophisticated validation
        return phoneNumber != null && phoneNumber.matches("\\d{10}"); // Assumes 10-digit phone numbers
    }
}
