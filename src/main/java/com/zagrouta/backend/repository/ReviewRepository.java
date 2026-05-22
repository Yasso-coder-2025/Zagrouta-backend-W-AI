package com.zagrouta.backend.repository;

import com.zagrouta.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByServiceIdOrderByCreatedAtDesc(Long serviceId);
}
