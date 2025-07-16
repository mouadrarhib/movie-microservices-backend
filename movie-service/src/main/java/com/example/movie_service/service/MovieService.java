package com.example.movie_service.service;

import com.example.movie_service.dto.MovieRequestDTO;
import com.example.movie_service.dto.MovieResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    
    // CRUD operations
    MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO);
    
    Optional<MovieResponseDTO> getMovieById(Long id);
    
    Page<MovieResponseDTO> getAllMovies(Pageable pageable);
    
    MovieResponseDTO updateMovie(Long id, MovieRequestDTO movieRequestDTO);
    
    void deleteMovie(Long id);
    
    // Search operations
    List<MovieResponseDTO> searchMoviesByTitle(String title);
    
    List<MovieResponseDTO> getMoviesByGenre(String genre);
    
    List<MovieResponseDTO> getMoviesByReleaseYear(Integer releaseYear);
    
    List<MovieResponseDTO> getMoviesByYearRange(Integer startYear, Integer endYear);
    
    List<MovieResponseDTO> searchMovies(String title, String genre, Integer releaseYear);
    
    // Utility operations
    List<String> getAllGenres();
    
    List<Integer> getAllReleaseYears();
    
    boolean existsById(Long id);
}

