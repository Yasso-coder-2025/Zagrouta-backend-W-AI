package com.zagrouta.backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @JsonIgnoreProperties({"password", "createdAt"})
    private User customer;

    // We store vendorId explicitly
    @Column(name = "vendor_id", nullable = false)
    private Long vendorId;

    @Column(name = "service_id", nullable = false)
    private Long serviceId;
    
    @Column(name = "service_name")
    private String serviceName;
    
    @Column(name = "service_price")
    private String servicePrice;
    
    @Column(name = "vendor_name")
    private String vendorName;

    @Column(name = "booking_date")
    private String bookingDate;

    // PENDING, CONFIRMED, REJECTED, CANCELLED
    @Column(nullable = false)
    private String status = "PENDING";

    @Column(name = "payment_method")
    private String paymentMethod = "CASH";

    @Column(name = "created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
