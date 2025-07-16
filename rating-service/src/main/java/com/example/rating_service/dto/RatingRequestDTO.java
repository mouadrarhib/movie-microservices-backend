package com.example.rating_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;

public class RatingRequestDTO {
    
    @NotNull(message = "L'ID du film est obligatoire")
    private Long movieId;
    
    @NotNull(message = "La note est obligatoire")
    @Min(value = 1, message = "La note doit être comprise entre 1 et 5")
    @Max(value = 5, message = "La note doit être comprise entre 1 et 5")
    private Integer rating;
    
    private String userSessionId;
    
    // Constructeurs
    public RatingRequestDTO() {}
    
    public RatingRequestDTO(Long movieId, Integer rating, String userSessionId) {
        this.movieId = movieId;
        this.rating = rating;
        this.userSessionId = userSessionId;
    }
    
    // Getters et Setters
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
    
    @Override
    public String toString() {
        return "RatingRequestDTO{" +
                "movieId=" + movieId +
                ", rating=" + rating +
                ", userSessionId='" + userSessionId + '\'' +
                '}';
    }
}

