package com.house.property_rent.service;

import com.house.property_rent.model.Property;
import com.house.property_rent.model.User;
import com.house.property_rent.repository.PropertyRepository;
import com.house.property_rent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public String bookProperty(Long userId, Long propertyId) {
        // Fetch property and user from the database
        Property property = propertyRepository.findById(propertyId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (property == null) {
            return "Error: Property not found!";
        }

        if (user == null) {
            return "Error: User not found!";
        }

        if (!property.isAvailabilityStatus()) {
            return "Error: Property is unavailable!";
        }

        if (user.getWalletBalance() < property.getRentAmount()) {
            return "Error: Insufficient wallet balance!";
        }

        // Deduct rent and update property availability
        user.setWalletBalance(user.getWalletBalance() - property.getRentAmount());
        property.setAvailabilityStatus(false);

        // Save updated user and property
        userRepository.save(user);
        propertyRepository.save(property);

        return "Booking successful! Property booked: " + property.getPropertyName();
    }
}
