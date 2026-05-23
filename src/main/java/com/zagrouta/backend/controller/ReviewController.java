package com.zagrouta.backend.controller;

import com.zagrouta.backend.entity.Review;
import com.zagrouta.backend.service.ReviewService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/service/{serviceId}")
    public List<Review> getReviewsByService(@PathVariable Long serviceId) {
        return reviewService.getReviewsByService(serviceId);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<Review> getReviewsByVendor(@PathVariable Long vendorId) {
        return reviewService.getReviewsByVendor(vendorId);
    }

    @PostMapping("/add")
    public Review addReview(@RequestBody Map<String, Object> payload) {
        Long serviceId = Long.valueOf(payload.get("serviceId").toString());
        Long userId = Long.valueOf(payload.get("userId").toString());
        String comment = payload.get("comment").toString();
        Integer rating = Integer.valueOf(payload.get("rating").toString());

        return reviewService.addReview(serviceId, userId, comment, rating);
    }
}
