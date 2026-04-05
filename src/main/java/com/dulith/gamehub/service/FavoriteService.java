package com.dulith.gamehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dulith.gamehub.entity.Favorite;
import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.repository.FavoriteRepository;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public boolean isFavorite(User user, Game game) {
        return favoriteRepository.findByUserAndGame(user, game).isPresent();
    }

    public void addToFavorites(User user, Game game) {
        if (!isFavorite(user, game)) {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setGame(game);
            favoriteRepository.save(favorite);
        }
    }

    public void removeFromFavorites(User user, Game game) {
        favoriteRepository.findByUserAndGame(user, game)
                .ifPresent(favoriteRepository::delete);
    }

    public boolean toggleFavorite(User user, Game game) {
        if (isFavorite(user, game)) {
            removeFromFavorites(user, game);
            return false;
        } else {
            addToFavorites(user, game);
            return true;
        }
    }

    public List<Favorite> getFavoriteItems(User user) {
        return favoriteRepository.findByUser(user);
    }

    public List<Game> getFavoriteGames(User user) {
        return favoriteRepository.findByUser(user)
                .stream()
                .map(Favorite::getGame)
                .toList();
    }

    public long getFavoriteCount(User user) {
        return favoriteRepository.countByUser(user);
    }
}