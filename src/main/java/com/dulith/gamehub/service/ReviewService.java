package com.dulith.gamehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.Review;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.repository.ReviewRepository;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public List<Review> getReviewsByGame(Game game) {
        return reviewRepository.findByGame(game);
    }

    public Review getReviewByUserAndGame(User user, Game game) {
        return reviewRepository.findByUserAndGame(user, game);
    }

    public void saveOrUpdateReview(User user, Game game, Integer rating, String comment) {
        Review existingReview = reviewRepository.findByUserAndGame(user, game);

        if (existingReview != null) {
            existingReview.setRating(rating);
            existingReview.setComment(comment);
            reviewRepository.save(existingReview);
            return;
        }

        Review review = new Review();
        review.setUser(user);
        review.setGame(game);
        review.setRating(rating);
        review.setComment(comment);

        reviewRepository.save(review);
    }

    public double getAverageRating(Game game) {
        List<Review> reviews = reviewRepository.findByGame(game);

        if (reviews.isEmpty()) {
            return 0.0;
        }

        int total = reviews.stream()
                .mapToInt(review -> review.getRating() != null ? review.getRating() : 0)
                .sum();

        return (double) total / reviews.size();
    }

    public int getReviewCount(Game game) {
        return reviewRepository.findByGame(game).size();
    }
}