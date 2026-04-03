package com.dulith.gamehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByGame(Game game);
}