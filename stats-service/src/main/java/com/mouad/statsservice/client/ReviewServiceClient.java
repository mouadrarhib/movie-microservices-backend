package com.mouad.statsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "review-service")
public interface ReviewServiceClient {
    
    @GetMapping("/api/reviews/movie/{movieId}/count")
    Object getReviewCount(@PathVariable("movieId") Long movieId);
}

