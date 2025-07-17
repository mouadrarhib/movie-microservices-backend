package com.example.movie_service.controller;


import com.example.movie_service.dto.MovieRequestDTO;
import com.example.movie_service.dto.MovieResponseDTO;
import com.example.movie_service.helpers.ApiResponse;
import com.example.movie_service.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movies")
@Tag(name = "Movie Management", description = "API pour la gestion du catalogue de films")
@CrossOrigin(origins = "*")
public class MovieController {
    
    private final MovieService movieService;
    
    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }
    
    @PostMapping
    @Operation(summary = "Créer un nouveau film", description = "Ajoute un nouveau film au catalogue")
    public ResponseEntity<ApiResponse<MovieResponseDTO>> createMovie(
            @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO createdMovie = movieService.createMovie(movieRequestDTO);
        ApiResponse<MovieResponseDTO> response = ApiResponse.success("Film créé avec succès", createdMovie);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Obtenir un film par ID", description = "Récupère les détails d'un film spécifique")
    public ResponseEntity<ApiResponse<MovieResponseDTO>> getMovieById(
            @Parameter(description = "ID du film") @PathVariable Long id) {
        Optional<MovieResponseDTO> movie = movieService.getMovieById(id);
        if (movie.isPresent()) {
            ApiResponse<MovieResponseDTO> response = ApiResponse.success(movie.get());
            return ResponseEntity.ok(response);
        } else {
            ApiResponse<MovieResponseDTO> response = ApiResponse.error("Film non trouvé");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping
    @Operation(summary = "Obtenir tous les films", description = "Récupère la liste paginée de tous les films")
    public ResponseEntity<ApiResponse<Page<MovieResponseDTO>>> getAllMovies(
            @Parameter(description = "Numéro de page (commence à 0)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Taille de la page") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Champ de tri") @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(description = "Direction du tri") @RequestParam(defaultValue = "asc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<MovieResponseDTO> movies = movieService.getAllMovies(pageable);
        ApiResponse<Page<MovieResponseDTO>> response = ApiResponse.success(movies);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un film", description = "Met à jour les informations d'un film existant")
    public ResponseEntity<ApiResponse<MovieResponseDTO>> updateMovie(
            @Parameter(description = "ID du film") @PathVariable Long id,
            @Valid @RequestBody MovieRequestDTO movieRequestDTO) {
        MovieResponseDTO updatedMovie = movieService.updateMovie(id, movieRequestDTO);
        ApiResponse<MovieResponseDTO> response = ApiResponse.success("Film mis à jour avec succès", updatedMovie);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un film", description = "Supprime un film du catalogue")
    public ResponseEntity<ApiResponse<Object>> deleteMovie(
            @Parameter(description = "ID du film") @PathVariable Long id) {
        movieService.deleteMovie(id);
        ApiResponse<Object> response = ApiResponse.success("Film supprimé avec succès", null);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    @Operation(summary = "Rechercher des films", description = "Recherche des films par titre, genre et/ou année")
    public ResponseEntity<ApiResponse<List<MovieResponseDTO>>> searchMovies(
            @Parameter(description = "Titre du film (recherche partielle)") @RequestParam(required = false) String title,
            @Parameter(description = "Genre du film") @RequestParam(required = false) String genre,
            @Parameter(description = "Année de sortie") @RequestParam(required = false) Integer releaseYear) {
        
        List<MovieResponseDTO> movies = movieService.searchMovies(title, genre, releaseYear);
        ApiResponse<List<MovieResponseDTO>> response = ApiResponse.success(movies);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search/title")
    @Operation(summary = "Rechercher par titre", description = "Recherche des films par titre (recherche partielle)")
    public ResponseEntity<ApiResponse<List<MovieResponseDTO>>> searchMoviesByTitle(
            @Parameter(description = "Titre du film") @RequestParam String title) {
        List<MovieResponseDTO> movies = movieService.searchMoviesByTitle(title);
        ApiResponse<List<MovieResponseDTO>> response = ApiResponse.success(movies);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/genre/{genre}")
    @Operation(summary = "Obtenir films par genre", description = "Récupère tous les films d'un genre spécifique")
    public ResponseEntity<ApiResponse<List<MovieResponseDTO>>> getMoviesByGenre(
            @Parameter(description = "Genre du film") @PathVariable String genre) {
        List<MovieResponseDTO> movies = movieService.getMoviesByGenre(genre);
        ApiResponse<List<MovieResponseDTO>> response = ApiResponse.success(movies);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/year/{year}")
    @Operation(summary = "Obtenir films par année", description = "Récupère tous les films d'une année spécifique")
    public ResponseEntity<ApiResponse<List<MovieResponseDTO>>> getMoviesByYear(
            @Parameter(description = "Année de sortie") @PathVariable Integer year) {
        List<MovieResponseDTO> movies = movieService.getMoviesByReleaseYear(year);
        ApiResponse<List<MovieResponseDTO>> response = ApiResponse.success(movies);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/year-range")
    @Operation(summary = "Obtenir films par plage d'années", description = "Récupère les films dans une plage d'années")
    public ResponseEntity<ApiResponse<List<MovieResponseDTO>>> getMoviesByYearRange(
            @Parameter(description = "Année de début") @RequestParam Integer startYear,
            @Parameter(description = "Année de fin") @RequestParam Integer endYear) {
        List<MovieResponseDTO> movies = movieService.getMoviesByYearRange(startYear, endYear);
        ApiResponse<List<MovieResponseDTO>> response = ApiResponse.success(movies);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/genres")
    @Operation(summary = "Obtenir tous les genres", description = "Récupère la liste de tous les genres disponibles")
    public ResponseEntity<ApiResponse<List<String>>> getAllGenres() {
        List<String> genres = movieService.getAllGenres();
        ApiResponse<List<String>> response = ApiResponse.success(genres);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/years")
    @Operation(summary = "Obtenir toutes les années", description = "Récupère la liste de toutes les années de sortie")
    public ResponseEntity<ApiResponse<List<Integer>>> getAllReleaseYears() {
        List<Integer> years = movieService.getAllReleaseYears();
        ApiResponse<List<Integer>> response = ApiResponse.success(years);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{id}/exists")
    @Operation(summary = "Vérifier l'existence d'un film", description = "Vérifie si un film existe par son ID")
    public ResponseEntity<ApiResponse<Boolean>> checkMovieExists(
            @Parameter(description = "ID du film") @PathVariable Long id) {
        boolean exists = movieService.existsById(id);
        ApiResponse<Boolean> response = ApiResponse.success(exists);
        return ResponseEntity.ok(response);
    }
}

