package com.mouad.statsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rating-service")
public interface RatingServiceClient {
    
    @GetMapping("/api/ratings/movie/{movieId}/average")
    Object getAverageRating(@PathVariable("movieId") Long movieId);
    
    @GetMapping("/api/ratings/movie/{movieId}/count")
    Object getRatingCount(@PathVariable("movieId") Long movieId);
}

