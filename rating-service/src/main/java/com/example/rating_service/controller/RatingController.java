package com.example.rating_service.controller;

import com.example.rating_service.dto.RatingRequestDTO;
import com.example.rating_service.dto.RatingResponseDTO;
import com.example.rating_service.helper.ApiResponse;
import com.example.rating_service.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Rating Management", description = "API pour la gestion des notes de films")
@CrossOrigin(origins = "*")
public class RatingController {
    
    private final RatingService ratingService;
    
    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }
    
    @PostMapping
    @Operation(summary = "Créer ou mettre à jour une note", description = "Ajoute une nouvelle note ou met à jour une note existante")
    public ResponseEntity<ApiResponse<RatingResponseDTO>> createRating(
            @Valid @RequestBody RatingRequestDTO ratingRequestDTO) {
        RatingResponseDTO createdRating = ratingService.createRating(ratingRequestDTO);
        ApiResponse<RatingResponseDTO> response = ApiResponse.success("Note enregistrée avec succès", createdRating);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir une note par ID", description = "Récupère les détails d'une note spécifique")
    public ResponseEntity<ApiResponse<RatingResponseDTO>> getRatingById(
            @Parameter(description = "ID de la note") @PathVariable Long id) {
        Optional<RatingResponseDTO> rating = ratingService.getRatingById(id);
        if (rating.isPresent()) {
            ApiResponse<RatingResponseDTO> response = ApiResponse.success(rating.get());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<RatingResponseDTO> response = ApiResponse.error("Note non trouvée");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    @Operation(summary = "Obtenir toutes les notes", description = "Récupère la liste de toutes les notes")
    public ResponseEntity<ApiResponse<List<RatingResponseDTO>>> getAllRatings() {
        List<RatingResponseDTO> ratings = ratingService.getAllRatings();
        ApiResponse<List<RatingResponseDTO>> response = ApiResponse.success(ratings);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Obtenir les notes d'un film", description = "Récupère toutes les notes pour un film spécifique")
    public ResponseEntity<ApiResponse<List<RatingResponseDTO>>> getRatingsByMovieId(
            @Parameter(description = "ID du film") @PathVariable Long movieId) {
        List<RatingResponseDTO> ratings = ratingService.getRatingsByMovieId(movieId);
        ApiResponse<List<RatingResponseDTO>> response = ApiResponse.success(ratings);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}/average")
    @Operation(summary = "Obtenir la note moyenne d'un film", description = "Calcule la note moyenne pour un film")
    public ResponseEntity<ApiResponse<Double>> getAverageRating(
            @Parameter(description = "ID du film") @PathVariable Long movieId) {
        Double averageRating = ratingService.getAverageRatingByMovieId(movieId);
        ApiResponse<Double> response = ApiResponse.success(averageRating);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}/count")
    @Operation(summary = "Obtenir le nombre de notes d'un film", description = "Compte le nombre total de notes pour un film")
    public ResponseEntity<ApiResponse<Long>> getRatingCount(
            @Parameter(description = "ID du film") @PathVariable Long movieId) {
        Long count = ratingService.getRatingCountByMovieId(movieId);
        ApiResponse<Long> response = ApiResponse.success(count);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}/distribution")
    @Operation(summary = "Obtenir la distribution des notes", description = "Récupère la distribution des notes pour un film")
    public ResponseEntity<ApiResponse<Map<Integer, Long>>> getRatingDistribution(
            @Parameter(description = "ID du film") @PathVariable Long movieId) {
        Map<Integer, Long> distribution = ratingService.getRatingDistributionByMovieId(movieId);
        ApiResponse<Map<Integer, Long>> response = ApiResponse.success(distribution);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une note", description = "Supprime une note spécifique")
    public ResponseEntity<ApiResponse<Object>> deleteRating(
            @Parameter(description = "ID de la note") @PathVariable Long id) {
        ratingService.deleteRating(id);
        ApiResponse<Object> response = ApiResponse.success("Note supprimée avec succès", null);
        return ResponseEntity.ok(response);
    }
}

