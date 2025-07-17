package com.example.review_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ReviewRequestDTO {
    
    @NotNull(message = "L'ID du film est obligatoire")
    private Long movieId;
    
    @NotBlank(message = "Le commentaire est obligatoire")
    @Size(min = 10, max = 1000, message = "Le commentaire doit contenir entre 10 et 1000 caractères")
    private String comment;
    
    private String reviewerName;
    private String userSessionId;
    
    // Constructeurs
    public ReviewRequestDTO() {}
    
    public ReviewRequestDTO(Long movieId, String comment, String reviewerName, String userSessionId) {
        this.movieId = movieId;
        this.comment = comment;
        this.reviewerName = reviewerName;
        this.userSessionId = userSessionId;
    }
    
    // Getters et Setters
    public Long getMovieId() {
        return movieId;
    }
    
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public String getReviewerName() {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    
    public String getUserSessionId() {
        return userSessionId;
    }
    
    public void setUserSessionId(String userSessionId) {
        this.userSessionId = userSessionId;
    }
    
    @Override
    public String toString() {
        return "ReviewRequestDTO{" +
                "movieId=" + movieId +
                ", comment='" + comment + '\'' +
                ", reviewerName='" + reviewerName + '\'' +
                ", userSessionId='" + userSessionId + '\'' +
                '}';
    }
}

