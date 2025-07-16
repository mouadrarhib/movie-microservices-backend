package com.example.rating_service.repository;

import com.example.rating_service.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    
    // Obtenir toutes les notes pour un film
    List<Rating> findByMovieId(Long movieId);
    
    // Vérifier si un utilisateur a déjà noté un film
    Optional<Rating> findByMovieIdAndUserSessionId(Long movieId, String userSessionId);
    
    // Compter le nombre de notes pour un film
    long countByMovieId(Long movieId);
    
    // Calculer la note moyenne pour un film
    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.movieId = :movieId")
    Double findAverageRatingByMovieId(@Param("movieId") Long movieId);
    
    // Obtenir la distribution des notes pour un film
    @Query("SELECT r.rating, COUNT(r) FROM Rating r WHERE r.movieId = :movieId GROUP BY r.rating ORDER BY r.rating")
    List<Object[]> findRatingDistributionByMovieId(@Param("movieId") Long movieId);
    
    // Obtenir les statistiques globales
    @Query("SELECT COUNT(r), AVG(r.rating) FROM Rating r")
    Object[] findGlobalRatingStats();
    
    // Supprimer toutes les notes d'un film
    void deleteByMovieId(Long movieId);
}

