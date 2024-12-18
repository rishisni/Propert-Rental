package com.house.property_rent.controller;

import com.house.property_rent.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/book")
    public String bookProperty(@RequestParam Long userId, @RequestParam Long propertyId) {
        return bookingService.bookProperty(userId, propertyId);
    }
}

