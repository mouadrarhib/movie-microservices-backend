package com.example.review_service.service;

import com.example.review_service.dto.ReviewRequestDTO;
import com.example.review_service.dto.ReviewResponseDTO;
import com.example.review_service.exception.ReviewNotFoundException;
import com.example.review_service.model.Review;
import com.example.review_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {
    
    private final ReviewRepository reviewRepository;
    
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    
    @Override
    public ReviewResponseDTO createReview(ReviewRequestDTO reviewRequestDTO) {
        // Vérifier si l'utilisateur a déjà commenté ce film
        Optional<Review> existingReview = reviewRepository.findByMovieIdAndUserSessionId(
                reviewRequestDTO.getMovieId(), reviewRequestDTO.getUserSessionId());
        
        if (existingReview.isPresent()) {
            // Mettre à jour le commentaire existant
            Review review = existingReview.get();
            review.setComment(reviewRequestDTO.getComment());
            review.setReviewerName(reviewRequestDTO.getReviewerName());
            Review savedReview = reviewRepository.save(review);
            return convertToResponseDTO(savedReview);
        } else {
            // Créer un nouveau commentaire
            Review review = new Review(
                reviewRequestDTO.getMovieId(),
                reviewRequestDTO.getComment(),
                reviewRequestDTO.getReviewerName(),
                reviewRequestDTO.getUserSessionId()
            );
            Review savedReview = reviewRepository.save(review);
            return convertToResponseDTO(savedReview);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewResponseDTO> getReviewById(Long id) {
        return reviewRepository.findById(id)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public ReviewResponseDTO updateReview(Long id, ReviewRequestDTO reviewRequestDTO) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Commentaire non trouvé avec l'ID: " + id));
        
        review.setComment(reviewRequestDTO.getComment());
        review.setReviewerName(reviewRequestDTO.getReviewerName());
        Review updatedReview = reviewRepository.save(review);
        return convertToResponseDTO(updatedReview);
    }
    
    @Override
    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new ReviewNotFoundException("Commentaire non trouvé avec l'ID: " + id);
        }
        reviewRepository.deleteById(id);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> getReviewsByMovieId(Long movieId) {
        return reviewRepository.findByMovieIdOrderByCreatedAtDesc(movieId)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getReviewCountByMovieId(Long movieId) {
        return reviewRepository.countByMovieId(movieId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponseDTO> searchReviewsByKeyword(Long movieId, String keyword) {
        return reviewRepository.findByMovieIdAndCommentContaining(movieId, keyword)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public Optional<ReviewResponseDTO> getUserReviewForMovie(Long movieId, String userSessionId) {
        return reviewRepository.findByMovieIdAndUserSessionId(movieId, userSessionId)
                .map(this::convertToResponseDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean hasUserReviewedMovie(Long movieId, String userSessionId) {
        return reviewRepository.findByMovieIdAndUserSessionId(movieId, userSessionId).isPresent();
    }
    
    @Override
    public void deleteReviewsByMovieId(Long movieId) {
        reviewRepository.deleteByMovieId(movieId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getTotalReviewCount() {
        return reviewRepository.countAllReviews();
    }
    
    private ReviewResponseDTO convertToResponseDTO(Review review) {
        return new ReviewResponseDTO(
            review.getId(),
            review.getMovieId(),
            review.getComment(),
            review.getReviewerName(),
            review.getUserSessionId(),
            review.getCreatedAt(),
            review.getUpdatedAt()
        );
    }
}

