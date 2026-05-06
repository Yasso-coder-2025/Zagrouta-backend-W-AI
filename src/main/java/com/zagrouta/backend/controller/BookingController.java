package com.zagrouta.backend.controller;

import com.zagrouta.backend.entity.Booking;
import com.zagrouta.backend.service.BookingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/add/{customerId}")
    public Booking addBooking(@RequestBody Booking booking, @PathVariable Long customerId) {
        return bookingService.createBooking(booking, customerId);
    }

    @GetMapping("/customer/{customerId}")
    public List<Booking> getCustomerBookings(@PathVariable Long customerId) {
        return bookingService.getCustomerBookings(customerId);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<Booking> getVendorBookings(@PathVariable Long vendorId) {
        return bookingService.getVendorBookings(vendorId);
    }

    @PutMapping("/{bookingId}/status")
    public Booking updateStatus(@PathVariable Long bookingId, @RequestBody java.util.Map<String, String> body) {
        return bookingService.updateBookingStatus(bookingId, body.get("status"));
    }
}
