package com.zagrouta.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users") // لازم يكون نفس اسم الجدول في الداتابيز
@Data // بتعمل الـ Getters والـ Setters لوحدها (بفضل مكتبة Lombok)
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role; // (CUSTOMER, VENDOR, ADMIN)

    private String phone;

    private String gender; // (MALE, FEMALE)

    @Column(name = "created_at")
    @com.fasterxml.jackson.annotation.JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // عشان التاريخ يطلع بشكل مرتب في الرد
    private LocalDateTime createdAt = LocalDateTime.now();
}