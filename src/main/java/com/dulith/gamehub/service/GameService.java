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

    public long countGames() {
        return gameRepository.count();
    }

    public List<Game> getRelatedGames(Long currentGameId) {
        return gameRepository.findAll()
                .stream()
                .filter(game -> !game.getId().equals(currentGameId))
                .limit(3)
                .toList();
    }
}