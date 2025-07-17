package com.example.review_service.controller;


import com.example.review_service.dto.ReviewRequestDTO;
import com.example.review_service.dto.ReviewResponseDTO;
import com.example.review_service.helper.ApiResponse;
import com.example.review_service.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@Tag(name = "Review Management", description = "API pour la gestion des commentaires de films")
@CrossOrigin(origins = "*")
public class ReviewController {
    
    private final ReviewService reviewService;
    
    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }
    
    @PostMapping
    @Operation(summary = "Créer ou mettre à jour un commentaire", description = "Ajoute un nouveau commentaire ou met à jour un commentaire existant")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> createReview(
            @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        ReviewResponseDTO createdReview = reviewService.createReview(reviewRequestDTO);
        ApiResponse<ReviewResponseDTO> response = ApiResponse.success("Commentaire enregistré avec succès", createdReview);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un commentaire par ID", description = "Récupère les détails d'un commentaire spécifique")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> getReviewById(
            @Parameter(description = "ID du commentaire") @PathVariable Long id) {
        Optional<ReviewResponseDTO> review = reviewService.getReviewById(id);
        if (review.isPresent()) {
            ApiResponse<ReviewResponseDTO> response = ApiResponse.success(review.get());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<ReviewResponseDTO> response = ApiResponse.error("Commentaire non trouvé");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    @Operation(summary = "Obtenir tous les commentaires", description = "Récupère la liste de tous les commentaires")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> getAllReviews() {
        List<ReviewResponseDTO> reviews = reviewService.getAllReviews();
        ApiResponse<List<ReviewResponseDTO>> response = ApiResponse.success(reviews);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Obtenir les commentaires d'un film", description = "Récupère tous les commentaires pour un film spécifique")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> getReviewsByMovieId(
            @Parameter(description = "ID du film") @PathVariable Long movieId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewsByMovieId(movieId);
        ApiResponse<List<ReviewResponseDTO>> response = ApiResponse.success(reviews);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}/count")
    @Operation(summary = "Obtenir le nombre de commentaires d'un film", description = "Compte le nombre total de commentaires pour un film")
    public ResponseEntity<ApiResponse<Long>> getReviewCount(
            @Parameter(description = "ID du film") @PathVariable Long movieId) {
        Long count = reviewService.getReviewCountByMovieId(movieId);
        ApiResponse<Long> response = ApiResponse.success(count);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}/search")
    @Operation(summary = "Rechercher dans les commentaires", description = "Recherche des commentaires par mot-clé pour un film")
    public ResponseEntity<ApiResponse<List<ReviewResponseDTO>>> searchReviews(
            @Parameter(description = "ID du film") @PathVariable Long movieId,
            @Parameter(description = "Mot-clé de recherche") @RequestParam String keyword) {
        List<ReviewResponseDTO> reviews = reviewService.searchReviewsByKeyword(movieId, keyword);
        ApiResponse<List<ReviewResponseDTO>> response = ApiResponse.success(reviews);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un commentaire", description = "Met à jour un commentaire existant")
    public ResponseEntity<ApiResponse<ReviewResponseDTO>> updateReview(
            @Parameter(description = "ID du commentaire") @PathVariable Long id,
            @Valid @RequestBody ReviewRequestDTO reviewRequestDTO) {
        ReviewResponseDTO updatedReview = reviewService.updateReview(id, reviewRequestDTO);
        ApiResponse<ReviewResponseDTO> response = ApiResponse.success("Commentaire mis à jour avec succès", updatedReview);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un commentaire", description = "Supprime un commentaire spécifique")
    public ResponseEntity<ApiResponse<Object>> deleteReview(
            @Parameter(description = "ID du commentaire") @PathVariable Long id) {
        reviewService.deleteReview(id);
        ApiResponse<Object> response = ApiResponse.success("Commentaire supprimé avec succès", null);
        return ResponseEntity.ok(response);
    }
}

