package com.house.property_rent.service;

import com.house.property_rent.model.Booking;
import com.house.property_rent.model.Property;
import com.house.property_rent.model.User;
import com.house.property_rent.repository.BookingRepository;
import com.house.property_rent.repository.PropertyRepository;
import com.house.property_rent.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PropertyRepository propertyRepository;

    public String bookProperty(Long userId, Long propertyId) {
        User user = userRepository.findById(userId).orElse(null);
        Property property = propertyRepository.findById(propertyId).orElse(null);

        if (user == null) {
            return "Error: User not found!";
        }

        if (property == null || !property.isAvailabilityStatus()) {
            return "Error: Property not available!";
        }

        if (user.getWalletBalance() < property.getRentAmount()) {
            return "Error: Insufficient wallet balance!";
        }

        // Deduct rent amount from user's wallet
        user.setWalletBalance(user.getWalletBalance() - property.getRentAmount());
        property.setAvailabilityStatus(false);

        // Save booking
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setProperty(property);
        booking.setBookingDate(new Date());
        booking.setAmountPaid(property.getRentAmount());

        bookingRepository.save(booking);

        userRepository.save(user);
        propertyRepository.save(property);

        return "Booking successful! Booking ID: " + booking.getBookingId();
    }
}

