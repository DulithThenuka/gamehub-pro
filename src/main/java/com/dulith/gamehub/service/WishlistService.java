package com.dulith.gamehub.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.entity.Wishlist;
import com.dulith.gamehub.repository.WishlistRepository;

@Service
public class WishlistService {

    private final WishlistRepository wishlistRepository;

    public WishlistService(WishlistRepository wishlistRepository) {
        this.wishlistRepository = wishlistRepository;
    }

    public List<Game> getWishlistGames(User user) {
        return wishlistRepository.findByUser(user)
                .stream()
                .map(Wishlist::getGame)
                .collect(Collectors.toList());
    }

    public boolean isInWishlist(User user, Game game) {
        if (user == null || game == null) {
            return false;
        }

        return wishlistRepository.findByUserAndGame(user, game) != null;
    }

    public void toggleWishlist(User user, Game game) {
        Wishlist existing = wishlistRepository.findByUserAndGame(user, game);

        if (existing != null) {
            wishlistRepository.delete(existing);
            return;
        }

        Wishlist wishlist = new Wishlist();
        wishlist.setUser(user);
        wishlist.setGame(game);

        wishlistRepository.save(wishlist);
    }

    public int getWishlistCount(User user) {
        return wishlistRepository.findByUser(user).size();
    }
}