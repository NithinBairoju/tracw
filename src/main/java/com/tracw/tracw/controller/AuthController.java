package com.tracw.tracw.controller;

import com.tracw.tracw.model.User;
import com.tracw.tracw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> userMap) {
        String username = userMap.get("username");
        String password = userMap.get("password");
        String phoneNumber = userMap.get("phoneNumber");
        String balanceStr = userMap.get("balance");
        BigDecimal balance = new BigDecimal(balanceStr);

        try {
            User user = userService.registerUser(username, password, phoneNumber, balance);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
