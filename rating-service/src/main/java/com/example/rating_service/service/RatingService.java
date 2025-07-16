package com.example.rating_service.service;


import com.example.rating_service.dto.RatingRequestDTO;
import com.example.rating_service.dto.RatingResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface RatingService {
    
    // CRUD operations
    RatingResponseDTO createRating(RatingRequestDTO ratingRequestDTO);
    
    Optional<RatingResponseDTO> getRatingById(Long id);
    
    List<RatingResponseDTO> getAllRatings();
    
    RatingResponseDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO);
    
    void deleteRating(Long id);
    
    // Movie-specific operations
    List<RatingResponseDTO> getRatingsByMovieId(Long movieId);
    
    Double getAverageRatingByMovieId(Long movieId);
    
    Long getRatingCountByMovieId(Long movieId);
    
    Map<Integer, Long> getRatingDistributionByMovieId(Long movieId);
    
    // User session operations
    Optional<RatingResponseDTO> getUserRatingForMovie(Long movieId, String userSessionId);
    
    boolean hasUserRatedMovie(Long movieId, String userSessionId);
    
    // Utility operations
    void deleteRatingsByMovieId(Long movieId);
    
    Map<String, Object> getGlobalRatingStats();
}

