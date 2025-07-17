package com.example.review_service.service;


import com.example.review_service.dto.ReviewRequestDTO;
import com.example.review_service.dto.ReviewResponseDTO;

import java.util.List;
import java.util.Optional;

public interface ReviewService {
    
    // CRUD operations
    ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO);
    
    Optional<ReviewResponseDTO> getReviewById(Long id);
    
    List<ReviewResponseDTO> getAllReviews();
    
    ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO);
    
    void deleteReview(Long id);
    
    // Movie-specific operations
    List<ReviewResponseDTO> getReviewsByMovieId(Long movieId);
    
    Long getReviewCountByMovieId(Long movieId);
    
    List<ReviewResponseDTO> searchReviewsByKeyword(Long movieId, String keyword);
    
    // User session operations
    Optional<ReviewResponseDTO> getUserReviewForMovie(Long movieId, String userSessionId);
    
    boolean hasUserReviewedMovie(Long movieId, String userSessionId);
    
    // Utility operations
    void deleteReviewsByMovieId(Long movieId);
    
    Long getTotalReviewCount();
}

