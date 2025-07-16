package com.example.movie_service.repository;

import com.example.movie_service.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    
    // Recherche par titre (insensible à la casse)
    List<Movie> findByTitleContainingIgnoreCase(String title);
    
    // Recherche par genre (insensible à la casse)
    List<Movie> findByGenreIgnoreCase(String genre);
    
    // Recherche par année de sortie
    List<Movie> findByReleaseYear(Integer releaseYear);
    
    // Recherche par plage d'années
    List<Movie> findByReleaseYearBetween(Integer startYear, Integer endYear);
    
    // Recherche combinée titre et genre
    List<Movie> findByTitleContainingIgnoreCaseAndGenreIgnoreCase(String title, String genre);
    
    // Obtenir tous les genres distincts
    @Query("SELECT DISTINCT m.genre FROM Movie m ORDER BY m.genre")
    List<String> findDistinctGenres();
    
    // Obtenir toutes les années de sortie distinctes
    @Query("SELECT DISTINCT m.releaseYear FROM Movie m ORDER BY m.releaseYear DESC")
    List<Integer> findDistinctReleaseYears();
    
    // Recherche avancée avec plusieurs critères
    @Query("SELECT m FROM Movie m WHERE " +
           "(:title IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))) AND " +
           "(:genre IS NULL OR LOWER(m.genre) = LOWER(:genre)) AND " +
           "(:releaseYear IS NULL OR m.releaseYear = :releaseYear)")
    List<Movie> findMoviesByCriteria(@Param("title") String title, 
                                   @Param("genre") String genre, 
                                   @Param("releaseYear") Integer releaseYear);
}

