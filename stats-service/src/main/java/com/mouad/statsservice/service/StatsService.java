package com.mouad.statsservice.service;

import com.mouad.statsservice.dto.MovieStatsDTO;

public interface StatsService {
    
    MovieStatsDTO getMovieStats(Long movieId);
    
    boolean isValidMovie(Long movieId);
}

