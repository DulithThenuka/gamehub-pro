package com.dulith.gamehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.repository.GameRepository;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    public Game getGameById(Long id) {
        return gameRepository.findById(id).orElse(null);
    }

    public void saveGame(Game game) {
        gameRepository.save(game);
    }

    public void deleteGameById(Long id) {
        gameRepository.deleteById(id);
    }

    public long countGames() {
        return gameRepository.count();
    }

    public List<Game> getRelatedGames(Long currentGameId) {
        Game currentGame = getGameById(currentGameId);
        if (currentGame == null) {
            return List.of();
        }

        return gameRepository.findAll().stream()
                .filter(game -> !game.getId().equals(currentGameId))
                .filter(game -> game.getGenre() != null
                        && currentGame.getGenre() != null
                        && game.getGenre().equalsIgnoreCase(currentGame.getGenre()))
                .limit(4)
                .toList();
    }

    public List<Game> getFeaturedGames() {
        return gameRepository.findAll().stream().limit(5).toList();
    }

    public List<Game> getTopDiscountedGames() {
        return gameRepository.findAll().stream()
                .filter(game -> game.getDiscountPercent() != null && game.getDiscountPercent() > 0)
                .sorted((a, b) -> Integer.compare(
                        b.getDiscountPercent() != null ? b.getDiscountPercent() : 0,
                        a.getDiscountPercent() != null ? a.getDiscountPercent() : 0))
                .limit(5)
                .toList();
    }

    public List<Game> getFreeGames() {
        return gameRepository.findAll().stream()
                .filter(game -> game.getPrice() != null && game.getPrice() == 0)
                .limit(5)
                .toList();
    }
}