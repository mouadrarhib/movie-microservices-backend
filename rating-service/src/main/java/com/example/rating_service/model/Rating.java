package com.example.rating_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import java.time.LocalDateTime;

@Entity
@Table(name = "ratings")
public class Rating {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "L'ID du film est obligatoire")
    @Column(name = "movie_id", nullable = false)
    private Long movieId;
    
    @NotNull(message = "La note est obligatoire")
    @Min(value = 1, message = "La note doit être comprise entre 1 et 5")
    @Max(value = 5, message = "La note doit être comprise entre 1 et 5")
    @Column(nullable = false)
    private Integer rating;
    
    @Column(name = "user_session_id")
    private String userSessionId; // Pour éviter les doublons de notation par session
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructeurs
    public Rating() {}
    
    public Rating(Long movieId, Integer rating, String userSessionId) {
        this.movieId = movieId;
        this.rating = rating;
        this.userSessionId = userSessionId;
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
        return "Rating{" +
                "id=" + id +
                ", movieId=" + movieId +
                ", rating=" + rating +
                ", userSessionId='" + userSessionId + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}

