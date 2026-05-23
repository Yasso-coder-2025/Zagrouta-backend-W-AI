package com.zagrouta.backend.service;

import com.zagrouta.backend.entity.Review;
import com.zagrouta.backend.entity.ServiceEntity;
import com.zagrouta.backend.entity.User;
import com.zagrouta.backend.repository.ReviewRepository;
import com.zagrouta.backend.repository.ServiceRepository;
import com.zagrouta.backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ServiceRepository serviceRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, ServiceRepository serviceRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.serviceRepository = serviceRepository;
        this.userRepository = userRepository;
    }

    public List<Review> getReviewsByService(Long serviceId) {
        return reviewRepository.findByServiceIdOrderByCreatedAtDesc(serviceId);
    }

    public List<Review> getReviewsByVendor(Long vendorId) {
        return reviewRepository.findByVendorIdOrderByCreatedAtDesc(vendorId);
    }

    public Review addReview(Long serviceId, Long userId, String comment, Integer rating) {
        ServiceEntity service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Review review = new Review();
        review.setService(service);
        review.setUser(user);
        review.setComment(comment);
        review.setRating(rating);
        review.setCreatedAt(LocalDateTime.now());

        return reviewRepository.save(review);
    }
}
