package com.mouad.statsservice.controller;

import com.mouad.statsservice.dto.MovieStatsDTO;
import com.mouad.statsservice.helper.ApiResponse;
import com.mouad.statsservice.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stats")
@Tag(name = "Statistics", description = "API pour les statistiques de films")
@CrossOrigin(origins = "*")
public class StatsController {
    
    private final StatsService statsService;
    
    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }
    
    @GetMapping("/movie/{movieId}")
    @Operation(summary = "Obtenir les statistiques d'un film", description = "Récupère les statistiques complètes pour un film")
    public ResponseEntity<ApiResponse<MovieStatsDTO>> getMovieStats(
            @Parameter(description = "ID du film") @PathVariable Long movieId) {
        MovieStatsDTO stats = statsService.getMovieStats(movieId);
        ApiResponse<MovieStatsDTO> response = ApiResponse.success(stats);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/movie/{movieId}/exists")
    @Operation(summary = "Vérifier l'existence d'un film", description = "Vérifie si un film existe")
    public ResponseEntity<ApiResponse<Boolean>> checkMovieExists(
            @Parameter(description = "ID du film") @PathVariable Long movieId) {
        boolean exists = statsService.isValidMovie(movieId);
        ApiResponse<Boolean> response = ApiResponse.success(exists);
        return ResponseEntity.ok(response);
    }
}

