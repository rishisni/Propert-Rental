package com.house.property_rent.service;

import com.house.property_rent.model.User;
import com.house.property_rent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Error: User with this email already exists!";
        }

        // Save user to the database
        userRepository.save(user);
        return "User registered successfully with ID: " + user.getUserId();
    }

    // Login user
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        // Check if user exists and password matches
        if (user == null) {
            return "Error: User with this email does not exist!";
        }
        if (!user.getPassword().equals(password)) {
            return "Error: Invalid password!";
        }

        return "Login successful for user: " + user.getUserName();
    }

    // Get user details (Optional helper method for additional functionality)
    public User getUserDetails(String email) {
        return userRepository.findByEmail(email);
 
 
    }
}
