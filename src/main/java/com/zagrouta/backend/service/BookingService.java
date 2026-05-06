package com.zagrouta.backend.service;

import com.zagrouta.backend.entity.Booking;
import com.zagrouta.backend.entity.User;
import com.zagrouta.backend.repository.BookingRepository;
import com.zagrouta.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;

    public BookingService(BookingRepository bookingRepository, UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
    }

    public Booking createBooking(Booking booking, Long customerId) {
        User customer = userRepository.findById(customerId).orElseThrow(() -> new RuntimeException("Customer not found"));
        booking.setCustomer(customer);
        booking.setCreatedAt(LocalDateTime.now());
        if(booking.getStatus() == null) booking.setStatus("PENDING");
        return bookingRepository.save(booking);
    }

    public List<Booking> getCustomerBookings(Long customerId) {
        return bookingRepository.findByCustomerIdOrderByCreatedAtDesc(customerId);
    }

    public List<Booking> getVendorBookings(Long vendorId) {
        return bookingRepository.findByVendorIdOrderByCreatedAtDesc(vendorId);
    }

    public Booking updateBookingStatus(Long bookingId, String status) {
        Booking booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));
        booking.setStatus(status);
        return bookingRepository.save(booking);
    }
}
