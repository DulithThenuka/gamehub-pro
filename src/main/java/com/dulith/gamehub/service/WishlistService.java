package com.dulith.gamehub.service;

import java.util.List;

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

    public boolean isWishlisted(User user, Game game) {
        return wishlistRepository.findByUserAndGame(user, game).isPresent();
    }

    public void addToWishlist(User user, Game game) {
        if (!isWishlisted(user, game)) {
            Wishlist wishlist = new Wishlist();
            wishlist.setUser(user);
            wishlist.setGame(game);
            wishlistRepository.save(wishlist);
        }
    }

    public void removeFromWishlist(User user, Game game) {
        wishlistRepository.findByUserAndGame(user, game)
                .ifPresent(wishlistRepository::delete);
    }

    public boolean toggleWishlist(User user, Game game) {
        if (isWishlisted(user, game)) {
            removeFromWishlist(user, game);
            return false;
        } else {
            addToWishlist(user, game);
            return true;
        }
    }

    public List<Wishlist> getWishlistItems(User user) {
        return wishlistRepository.findByUser(user);
    }

    public List<Game> getWishlistGames(User user) {
        return wishlistRepository.findByUser(user)
                .stream()
                .map(Wishlist::getGame)
                .toList();
    }

    public long getWishlistCount(User user) {
        return wishlistRepository.countByUser(user);
    }
}