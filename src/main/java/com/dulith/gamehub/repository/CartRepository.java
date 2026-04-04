package com.dulith.gamehub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dulith.gamehub.entity.CartItem;
import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;

public interface CartRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByUserAndGame(User user, Game game);
    List<CartItem> findByUser(User user);
}