package com.app.quantitymeasurement.service;

import com.app.quantitymeasurement.entity.User;
import com.app.quantitymeasurement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUserIfNotExists(String username) {

        if (userRepository.findByUsername(username).isEmpty()) {

            User user = new User();
            user.setUsername(username);

            // Google login → password empty
            user.setPassword("GOOGLE_USER");

            user.setRole("ROLE_USER");

            userRepository.save(user);

            System.out.println("Google user saved: " + username);
        }
    }
}