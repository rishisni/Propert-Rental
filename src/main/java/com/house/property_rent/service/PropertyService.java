package com.house.property_rent.service;

import com.house.property_rent.model.Property;
import com.house.property_rent.repository.PropertyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    // Add a new property
    public String addProperty(Property property) {
        propertyRepository.save(property);
        return "Property added successfully with ID: " + property.getPropertyId();
    }

    // Get all properties
    public List<Property> getAllProperties() {
        return propertyRepository.findAll();
    }

    // Get available properties
    public List<Property> getAvailableProperties() {
        return propertyRepository.findByAvailabilityStatus(true);
    }
}

