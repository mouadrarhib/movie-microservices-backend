package com.example.rating_service.service;

import com.example.rating_service.dto.RatingRequestDTO;
import com.example.rating_service.dto.RatingResponseDTO;
import com.example.rating_service.exception.RatingNotFoundException;
import com.example.rating_service.model.Rating;
import com.example.rating_service.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RatingServiceImpl implements RatingService {
    
    private final RatingRepository ratingRepository;
    
    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
    
    @Override
    public RatingResponseDTO createRating(RatingRequestDTO ratingRequestDTO) {
        // Vérifier si l'utilisateur a déjà noté ce film
        Optional<Rating> existingRating = ratingRepository.findByMovieIdAndUserSessionId(
                ratingRequestDTO.getMovieId(), ratingRequestDTO.getUserSessionId());
        
        if (existingRating.isPresent()) {
            // Mettre à jour la note existante
            Rating rating = existingRating.get();
            rating.setRating(ratingRequestDTO.getRating());
            Rating savedRating = ratingRepository.save(rating);
            return convertToResponseDTO(savedRating);
        } else {
            // Créer une nouvelle note
            Rating rating = new Rating(
                ratingRequestDTO.getMovieId(),
                ratingRequestDTO.getRating(),
                ratingRequestDTO.getUserSessionId()
            );
            Rating savedRating = ratingRepository.save(rating);
            return convertToResponseDTO(savedRating);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<RatingResponseDTO> getRatingById(Long id) {
        return ratingRepository.findById(id)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RatingResponseDTO> getAllRatings() {
        return ratingRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public RatingResponseDTO updateRating(Long id, RatingRequestDTO ratingRequestDTO) {
        Rating rating = ratingRepository.findById(id)
                .orElseThrow(() -> new RatingNotFoundException("Note non trouvée avec l'ID: " + id));
        
        rating.setRating(ratingRequestDTO.getRating());
        Rating updatedRating = ratingRepository.save(rating);
        return convertToResponseDTO(updatedRating);
    }
    
    @Override
    public void deleteRating(Long id) {
        if (!ratingRepository.existsById(id)) {
            throw new RatingNotFoundException("Note non trouvée avec l'ID: " + id);
        }
        ratingRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<RatingResponseDTO> getRatingsByMovieId(Long movieId) {
        return ratingRepository.findByMovieId(movieId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Double getAverageRatingByMovieId(Long movieId) {
        return ratingRepository.findAverageRatingByMovieId(movieId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getRatingCountByMovieId(Long movieId) {
        return ratingRepository.countByMovieId(movieId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<Integer, Long> getRatingDistributionByMovieId(Long movieId) {
        List<Object[]> distribution = ratingRepository.findRatingDistributionByMovieId(movieId);
        Map<Integer, Long> result = new HashMap<>();
        for (Object[] row : distribution) {
            result.put((Integer) row[0], (Long) row[1]);
        }
        return result;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<RatingResponseDTO> getUserRatingForMovie(Long movieId, String userSessionId) {
        return ratingRepository.findByMovieIdAndUserSessionId(movieId, userSessionId)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean hasUserRatedMovie(Long movieId, String userSessionId) {
        return ratingRepository.findByMovieIdAndUserSessionId(movieId, userSessionId).isPresent();
    }
    
    @Override
    public void deleteRatingsByMovieId(Long movieId) {
        ratingRepository.deleteByMovieId(movieId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getGlobalRatingStats() {
        Object[] stats = ratingRepository.findGlobalRatingStats();
        Map<String, Object> result = new HashMap<>();
        result.put("totalRatings", stats[0]);
        result.put("averageRating", stats[1]);
        return result;
    }
    
    private RatingResponseDTO convertToResponseDTO(Rating rating) {
        return new RatingResponseDTO(
            rating.getId(),
            rating.getMovieId(),
            rating.getRating(),
            rating.getUserSessionId(),
            rating.getCreatedAt()
        );
    }
}

