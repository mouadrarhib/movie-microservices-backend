package com.example.movie_service.service;

import com.example.movie_service.dto.MovieRequestDTO;
import com.example.movie_service.dto.MovieResponseDTO;
import com.example.movie_service.exception.MovieNotFoundException;
import com.example.movie_service.model.Movie;
import com.example.movie_service.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class MovieServiceImpl implements MovieService {
    
    private final MovieRepository movieRepository;
    
    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    
    @Override
    public MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO) {
        Movie movie = new Movie(
            movieRequestDTO.getTitle(),
            movieRequestDTO.getGenre(),
            movieRequestDTO.getReleaseYear(),
            movieRequestDTO.getDescription()
        );
        
        Movie savedMovie = movieRepository.save(movie);
        return convertToResponseDTO(savedMovie);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<MovieResponseDTO> getMovieById(Long id) {
        return movieRepository.findById(id)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Page<MovieResponseDTO> getAllMovies(Pageable pageable) {
        return movieRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    public MovieResponseDTO updateMovie(Long id, MovieRequestDTO movieRequestDTO) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException("Film non trouvé avec l'ID: " + id));
        
        movie.setTitle(movieRequestDTO.getTitle());
        movie.setGenre(movieRequestDTO.getGenre());
        movie.setReleaseYear(movieRequestDTO.getReleaseYear());
        movie.setDescription(movieRequestDTO.getDescription());
        
        Movie updatedMovie = movieRepository.save(movie);
        return convertToResponseDTO(updatedMovie);
    }
    
    @Override
    public void deleteMovie(Long id) {
        if (!movieRepository.existsById(id)) {
            throw new MovieNotFoundException("Film non trouvé avec l'ID: " + id);
        }
        movieRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MovieResponseDTO> searchMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MovieResponseDTO> getMoviesByGenre(String genre) {
        return movieRepository.findByGenreIgnoreCase(genre)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MovieResponseDTO> getMoviesByReleaseYear(Integer releaseYear) {
        return movieRepository.findByReleaseYear(releaseYear)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MovieResponseDTO> getMoviesByYearRange(Integer startYear, Integer endYear) {
        return movieRepository.findByReleaseYearBetween(startYear, endYear)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MovieResponseDTO> searchMovies(String title, String genre, Integer releaseYear) {
        return movieRepository.findMoviesByCriteria(title, genre, releaseYear)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<String> getAllGenres() {
        return movieRepository.findDistinctGenres();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Integer> getAllReleaseYears() {
        return movieRepository.findDistinctReleaseYears();
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return movieRepository.existsById(id);
    }
    
    // Méthode utilitaire pour convertir Movie en MovieResponseDTO
    private MovieResponseDTO convertToResponseDTO(Movie movie) {
        return new MovieResponseDTO(
            movie.getId(),
            movie.getTitle(),
            movie.getGenre(),
            movie.getReleaseYear(),
            movie.getDescription(),
            movie.getCreatedAt(),
            movie.getUpdatedAt()
        );
    }
}

