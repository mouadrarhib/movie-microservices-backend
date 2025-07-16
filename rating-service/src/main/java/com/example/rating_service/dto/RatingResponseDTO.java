package com.example.rating_service.dto;

import java.time.LocalDateTime;

public class RatingResponseDTO {
    
    private Long id;
    private Long movieId;
    private Integer rating;
    private String userSessionId;
    private LocalDateTime createdAt;
    
    // Constructeurs
    public RatingResponseDTO() {}
    
    public RatingResponseDTO(Long id, Long movieId, Integer rating, String userSessionId, LocalDateTime createdAt) {
        this.id = id;
        this.movieId = movieId;
        this.rating = rating;
        this.userSessionId = userSessionId;
        this.createdAt = createdAt;
    }
    
    // Getters et Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getMovieId() {
        return movieId;
    }
    
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getUserSessionId() {
        return userSessionId;
    }
    
    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "RatingResponseDTO{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", rating=" + rating +
                ", userSessionId='" + userSessionId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

