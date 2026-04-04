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

    public void addReview(User user, Game game, int rating, String comment) {
        Review review = new Review();
        review.setUser(user);
        review.setGame(game);
        review.setRating(rating);
        review.setComment(comment);
        reviewRepository.save(review);
    }
}