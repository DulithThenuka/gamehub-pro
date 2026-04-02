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

    public List<Game> searchGames(String keyword, String genre, String platform) {
        boolean hasKeyword = keyword != null && !keyword.trim().isEmpty();
        boolean hasGenre = genre != null && !genre.trim().isEmpty();
        boolean hasPlatform = platform != null && !platform.trim().isEmpty();

        if (hasKeyword && hasGenre && hasPlatform) {
            return gameRepository.findByTitleContainingIgnoreCaseAndGenreIgnoreCaseAndPlatformIgnoreCase(
                    keyword.trim(), genre.trim(), platform.trim()
            );
        }

        if (hasKeyword && hasGenre) {
            return gameRepository.findByTitleContainingIgnoreCaseAndGenreIgnoreCase(
                    keyword.trim(), genre.trim()
            );
        }

        if (hasKeyword && hasPlatform) {
            return gameRepository.findByTitleContainingIgnoreCaseAndPlatformIgnoreCase(
                    keyword.trim(), platform.trim()
            );
        }

        if (hasGenre && hasPlatform) {
            return gameRepository.findByGenreIgnoreCaseAndPlatformIgnoreCase(
                    genre.trim(), platform.trim()
            );
        }

        if (hasKeyword) {
            return gameRepository.findByTitleContainingIgnoreCase(keyword.trim());
        }

        if (hasGenre) {
            return gameRepository.findByGenreIgnoreCase(genre.trim());
        }

        if (hasPlatform) {
            return gameRepository.findByPlatformIgnoreCase(platform.trim());
        }

        return gameRepository.findAll();
    }
}