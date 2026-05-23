package com.zagrouta.backend.repository;

import com.zagrouta.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByServiceIdOrderByCreatedAtDesc(Long serviceId);

    @Query("SELECT r FROM Review r WHERE r.service.user.id = :vendorId ORDER BY r.createdAt DESC")
    List<Review> findByVendorIdOrderByCreatedAtDesc(@Param("vendorId") Long vendorId);
}
