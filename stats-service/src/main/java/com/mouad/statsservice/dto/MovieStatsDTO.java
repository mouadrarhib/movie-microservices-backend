package com.mouad.statsservice.dto;

public class MovieStatsDTO {
    
    private Long movieId;
    private String movieTitle;
    private Double averageRating;
    private Long totalRatings;
    private Long totalReviews;
    
    // Constructeurs
    public MovieStatsDTO() {}
    
    public MovieStatsDTO(Long movieId, String movieTitle, Double averageRating, 
                        Long totalRatings, Long totalReviews) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.averageRating = averageRating;
        this.totalRatings = totalRatings;
        this.totalReviews = totalReviews;
    }
    
    // Getters et Setters
    public Long getMovieId() {
        return movieId;
    }
    
    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
    
    public String getMovieTitle() {
        return movieTitle;
    }
    
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }
    
    public Double getAverageRating() {
        return averageRating;
    }
    
    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }
    
    public Long getTotalRatings() {
        return totalRatings;
    }
    
    public void setTotalRatings(Long totalRatings) {
        this.totalRatings = totalRatings;
    }
    
    public Long getTotalReviews() {
        return totalReviews;
    }
    
    public void setTotalReviews(Long totalReviews) {
        this.totalReviews = totalReviews;
    }
    
    @Override
    public String toString() {
        return "MovieStatsDTO{" +
                "movieId=" + movieId +
                ", movieTitle='" + movieTitle + '\'' +
                ", averageRating=" + averageRating +
                ", totalRatings=" + totalRatings +
                ", totalReviews=" + totalReviews +
                '}';
    }
}

