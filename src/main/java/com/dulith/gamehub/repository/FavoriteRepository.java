package com.dulith.gamehub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dulith.gamehub.entity.Favorite;
import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByUserAndGame(User user, Game game);

    Optional<Favorite> findByUserAndGame(User user, Game game);

    List<Favorite> findByUser(User user);
}