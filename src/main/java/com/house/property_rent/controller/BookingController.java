package com.house.property_rent.controller;

import com.house.property_rent.model.User;
import com.house.property_rent.service.BookingService;
import com.house.property_rent.service.UserService;
import com.house.property_rent.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/book")
    public String bookProperty(@RequestHeader("Authorization") String token, @RequestParam Long propertyId) {
        String email = jwtUtil.extractEmail(token); // Extract email from token
        User user = userService.getUserDetails(email); // Fetch user details

        if (user == null || !user.getRole().equalsIgnoreCase("USER")) {
            return "Error: Only users can book properties.";
        }

        return bookingService.bookProperty(user.getUserId(), propertyId);
    }
}

