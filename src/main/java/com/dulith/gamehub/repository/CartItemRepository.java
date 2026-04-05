package com.dulith.gamehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dulith.gamehub.entity.CartItem;
import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByUser(User user);

    CartItem findByUserAndGame(User user, Game game);
}