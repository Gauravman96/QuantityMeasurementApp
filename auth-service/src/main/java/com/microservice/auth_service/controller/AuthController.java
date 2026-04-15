package com.microservice.auth_service.controller;

import com.microservice.auth_service.entity.User;
import com.microservice.auth_service.repository.UserRepository;
import com.microservice.auth_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth") 
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    // REGISTER
    @PostMapping("/register")
    public String register(@RequestBody User user) {

        userRepository.save(user);
        return "User registered successfully";
    }

    // LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody User user) {

        User existingUser = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!existingUser.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getUsername());

        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return response;
    }
}