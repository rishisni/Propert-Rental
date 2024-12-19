package com.house.property_rent.service;

import com.house.property_rent.model.User;
import com.house.property_rent.model.BlacklistedToken;
import com.house.property_rent.repository.UserRepository;
import com.house.property_rent.repository.BlacklistedTokenRepository;
import com.house.property_rent.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    // Register a new user
    public String registerUser(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Error: User with this email already exists!";
        }

        // Set default role if not provided
        if (user.getRole() == null || user.getRole().isEmpty()) {
            user.setRole("USER"); // Default to "USER"
        }

        // Set isVerified to true for now
        user.setVerified(true);

        // Save user to the database
        userRepository.save(user);
        return "User registered successfully with ID: " + user.getUserId();
    }

    // Login user and return a token
    public String loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
    
        // Check if user exists
        if (user == null) {
            return "Error: User with this email does not exist!";
        }
    
        // Validate the password
        if (!user.getPassword().equals(password)) {
            return "Error: Invalid password!";
        }
    
        // Generate a token for the user
        String token = jwtUtil.generateToken(user.getEmail());
        return "Login successful! Token: " + token;
    }
    

    // Get user details by email
    public User getUserDetails(String email) {
        return userRepository.findByEmail(email);
    }

    // Top-up wallet balance
    public String topUpWallet(String token, double amount) {
        String email = jwtUtil.extractEmail(token); // Extract email from token
        User user = userRepository.findByEmail(email);

        if (user == null) {
            return "Error: User not found!";
        }

        if (amount <= 0) {
            return "Error: Top-up amount must be greater than 0!";
        }

        // Add amount to the user's wallet
        user.setWalletBalance(user.getWalletBalance() + amount);
        userRepository.save(user);

        return "Wallet topped up successfully! New balance: " + user.getWalletBalance();
    }

    public String logout(String token) {
        if (blacklistedTokenRepository.existsByToken(token)) {
            return "Error: Token is already blacklisted.";
        }
    
        // Save token to blacklist
        blacklistedTokenRepository.save(new BlacklistedToken(token));
        return "Logout successful.";
    }
    
}
