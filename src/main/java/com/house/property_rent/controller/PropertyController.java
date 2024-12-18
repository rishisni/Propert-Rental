package com.house.property_rent.controller;

import com.house.property_rent.model.Property;
import com.house.property_rent.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    // Endpoint to add a new property
    @PostMapping("/add")
    public String addProperty(@RequestBody Property property) {
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
