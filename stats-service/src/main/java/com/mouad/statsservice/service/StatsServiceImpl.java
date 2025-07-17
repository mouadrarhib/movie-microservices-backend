package com.mouad.statsservice.service;

import com.mouad.statsservice.client.MovieServiceClient;
import com.mouad.statsservice.client.RatingServiceClient;
import com.mouad.statsservice.client.ReviewServiceClient;
import com.mouad.statsservice.dto.MovieStatsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatsServiceImpl implements StatsService {
    
    private final MovieServiceClient movieServiceClient;
    private final RatingServiceClient ratingServiceClient;
    private final ReviewServiceClient reviewServiceClient;
    
    @Autowired
    public StatsServiceImpl(MovieServiceClient movieServiceClient,
                           RatingServiceClient ratingServiceClient,
                           ReviewServiceClient reviewServiceClient) {
        this.movieServiceClient = movieServiceClient;
        this.ratingServiceClient = ratingServiceClient;
        this.reviewServiceClient = reviewServiceClient;
    }
    
    @Override
    public MovieStatsDTO getMovieStats(Long movieId) {
        try {
            // Récupérer les informations du film
            Object movieResponse = movieServiceClient.getMovieById(movieId);
            
            // Récupérer les statistiques de notation
            Object averageRatingResponse = ratingServiceClient.getAverageRating(movieId);
            Object ratingCountResponse = ratingServiceClient.getRatingCount(movieId);
            
            // Récupérer les statistiques de commentaires
            Object reviewCountResponse = reviewServiceClient.getReviewCount(movieId);
            
            // Construire le DTO de statistiques
            MovieStatsDTO stats = new MovieStatsDTO();
            stats.setMovieId(movieId);
            stats.setMovieTitle("Film " + movieId); // Simplification pour l'exemple
            stats.setAverageRating(0.0); // À extraire de la réponse
            stats.setTotalRatings(0L); // À extraire de la réponse
            stats.setTotalReviews(0L); // À extraire de la réponse
            
            return stats;
        } catch (Exception e) {
            // Gestion d'erreur simplifiée
            MovieStatsDTO stats = new MovieStatsDTO();
            stats.setMovieId(movieId);
            stats.setMovieTitle("Film non trouvé");
            stats.setAverageRating(0.0);
            stats.setTotalRatings(0L);
            stats.setTotalReviews(0L);
            return stats;
        }
    }
    
    @Override
    public boolean isValidMovie(Long movieId) {
        try {
            Object response = movieServiceClient.checkMovieExists(movieId);
            return true; // Simplification
        } catch (Exception e) {
            return false;
        }
    }
}

