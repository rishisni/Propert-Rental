package com.house.property_rent.controller;

import com.house.property_rent.model.User;
import com.house.property_rent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Endpoint to register a user
    @PostMapping("/register")
    public String registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    // Endpoint to login a user
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password) {
        return userService.loginUser(email, password);
    }

    // Endpoint to top-up wallet balance
    @PostMapping("/wallet/topup")
    public String topUpWallet(@RequestHeader("Authorization") String token, @RequestParam double amount) {
        return userService.topUpWallet(token, amount);
    }

    // Endpoint to logout a user
    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String token) {
        return userService.logout(token);
    }
}
