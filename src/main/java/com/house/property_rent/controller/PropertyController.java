package com.house.property_rent.controller;

import com.house.property_rent.model.Property;
import com.house.property_rent.model.User;
import com.house.property_rent.service.PropertyService;
import com.house.property_rent.service.UserService;
import com.house.property_rent.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    // Endpoint to add a new property
    @PostMapping("/add")
    public String addProperty(@RequestHeader("Authorization") String token, @RequestBody Property property) {
        String email = jwtUtil.extractEmail(token); // Extract email from token
        User user = userService.getUserDetails(email); // Fetch user details

        if (user == null || !user.getRole().equalsIgnoreCase("ADMIN")) {
            return "Error: Only admins can add properties.";
        }

        return propertyService.addProperty(property);
    }

    // Endpoint to get all properties
    @GetMapping("/all")
    public List<Property> getAllProperties() {
        return propertyService.getAllProperties();
    }

    // Endpoint to get available properties
    @GetMapping("/available")
    public List<Property> getAvailableProperties() {
        return propertyService.getAvailableProperties();
    }
}
