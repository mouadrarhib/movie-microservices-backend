package com.mouad.statsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "movie-service")
public interface MovieServiceClient {
    
    @GetMapping("/api/movies/{id}")
    Object getMovieById(@PathVariable("id") Long id);
    
    @GetMapping("/api/movies/{id}/exists")
    Object checkMovieExists(@PathVariable("id") Long id);
}

