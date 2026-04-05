package com.dulith.gamehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.Review;
import com.dulith.gamehub.entity.User;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByGame(Game game);

    Review findByUserAndGame(User user, Game game);
}