package com.example.review_service.repository;

import com.example.review_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    // Obtenir tous les commentaires pour un film
    List<Review> findByMovieIdOrderByCreatedAtDesc(Long movieId);
    
    // Vérifier si un utilisateur a déjà commenté un film
    Optional<Review> findByMovieIdAndUserSessionId(Long movieId, String userSessionId);
    
    // Compter le nombre de commentaires pour un film
    long countByMovieId(Long movieId);
    
    // Rechercher dans les commentaires par contenu
    @Query("SELECT r FROM Review r WHERE r.movieId = :movieId AND LOWER(r.comment) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Review> findByMovieIdAndCommentContaining(@Param("movieId") Long movieId, @Param("keyword") String keyword);
    
    // Obtenir les statistiques globales
    @Query("SELECT COUNT(r) FROM Review r")
    Long countAllReviews();
    
    // Supprimer tous les commentaires d'un film
    void deleteByMovieId(Long movieId);
}

