package com.example.movie_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MovieRequestDTO {
    
    @NotBlank(message = "Le titre est obligatoire")
    private String title;
    
    @NotBlank(message = "Le genre est obligatoire")
    private String genre;
    
    @NotNull(message = "L'année de sortie est obligatoire")
    @Min(value = 1900, message = "L'année de sortie doit être supérieure à 1900")
    @Max(value = 2030, message = "L'année de sortie doit être inférieure à 2030")
    private Integer releaseYear;
    
    private String description;
    
    // Constructeurs
    public MovieRequestDTO() {}
    
    public MovieRequestDTO(String title, String genre, Integer releaseYear, String description) {
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.description = description;
    }
    
    // Getters et Setters
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public Integer getReleaseYear() {
        return releaseYear;
    }
    
    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "MovieRequestDTO{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", releaseYear=" + releaseYear +
                ", description='" + description + '\'' +
                '}';
    }
}

