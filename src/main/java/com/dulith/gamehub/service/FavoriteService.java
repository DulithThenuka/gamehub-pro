package com.dulith.gamehub.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        return favoriteRepository.existsByUserAndGame(user, game);
    }

    public void addFavorite(User user, Game game) {
        if (!favoriteRepository.existsByUserAndGame(user, game)) {
            Favorite favorite = new Favorite();
            favorite.setUser(user);
            favorite.setGame(game);
            favoriteRepository.save(favorite);
        }
    }

    public void removeFavorite(User user, Game game) {
        favoriteRepository.findByUserAndGame(user, game)
                .ifPresent(favoriteRepository::delete);
    }

    public List<Game> getFavoriteGames(User user) {
        return favoriteRepository.findByUser(user)
                .stream()
                .map(Favorite::getGame)
                .collect(Collectors.toList());
    }

    public boolean toggleFavorite(User user, Game game) {
    Optional<Favorite> existing = favoriteRepository.findByUserAndGame(user, game);

    if (existing.isPresent()) {
        favoriteRepository.delete(existing.get());
        return false;
    } else {
        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setGame(game);
        favoriteRepository.save(favorite);
        return true;
    }
}
}