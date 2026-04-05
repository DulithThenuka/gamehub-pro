package com.dulith.gamehub.service;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
        return gameRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Game::getId))
                .collect(Collectors.toList());
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
            return getAllGames()
                    .stream()
                    .limit(4)
                    .collect(Collectors.toList());
        }

        String currentGenre = currentGame.getGenre();

        List<Game> sameGenreGames = gameRepository.findAll()
                .stream()
                .filter(game -> game.getId() != null && !game.getId().equals(currentGameId))
                .filter(game -> currentGenre != null
                        && game.getGenre() != null
                        && game.getGenre().equalsIgnoreCase(currentGenre))
                .limit(4)
                .collect(Collectors.toList());

        if (!sameGenreGames.isEmpty()) {
            return sameGenreGames;
        }

        return gameRepository.findAll()
                .stream()
                .filter(game -> game.getId() != null && !game.getId().equals(currentGameId))
                .limit(4)
                .collect(Collectors.toList());
    }

    public List<String> getAllGenres() {
        Set<String> genres = gameRepository.findAll()
                .stream()
                .map(Game::getGenre)
                .filter(genre -> genre != null && !genre.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return genres.stream().collect(Collectors.toList());
    }

    public List<String> getAllPlatforms() {
        Set<String> platforms = gameRepository.findAll()
                .stream()
                .map(Game::getPlatform)
                .filter(platform -> platform != null && !platform.isBlank())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        return platforms.stream().collect(Collectors.toList());
    }

    public List<Game> searchGames(String search, String genre, String platform) {
        return gameRepository.findAll()
                .stream()
                .filter(game -> matchesSearch(game, search))
                .filter(game -> matchesGenre(game, genre))
                .filter(game -> matchesPlatform(game, platform))
                .sorted(Comparator.comparing(Game::getId))
                .collect(Collectors.toList());
    }

    public List<Game> getFeaturedGames() {
        return gameRepository.findAll()
                .stream()
                .sorted(Comparator
                        .comparing(Game::getRating, Comparator.nullsLast(Comparator.reverseOrder()))
                        .thenComparing(Game::getDiscountPercent, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(4)
                .collect(Collectors.toList());
    }

    public List<Game> getNewReleases() {
        return gameRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Game::getId, Comparator.reverseOrder()))
                .limit(8)
                .collect(Collectors.toList());
    }

    public List<Game> getTopSellers() {
        return gameRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Game::getRating, Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(8)
                .collect(Collectors.toList());
    }

    public List<Game> getFreeToPlayGames() {
        return gameRepository.findAll()
                .stream()
                .filter(game -> {
                    Double price = game.getPrice();
                    Double discountedPrice = game.getDiscountedPrice();

                    return (price != null && price <= 0)
                            || (discountedPrice != null && discountedPrice <= 0);
                })
                .limit(8)
                .collect(Collectors.toList());
    }

    private boolean matchesSearch(Game game, String search) {
        if (search == null || search.isBlank()) {
            return true;
        }

        String keyword = search.trim().toLowerCase();

        return (game.getTitle() != null && game.getTitle().toLowerCase().contains(keyword))
                || (game.getGenre() != null && game.getGenre().toLowerCase().contains(keyword))
                || (game.getPlatform() != null && game.getPlatform().toLowerCase().contains(keyword));
    }

    private boolean matchesGenre(Game game, String genre) {
        if (genre == null || genre.isBlank()) {
            return true;
        }

        return game.getGenre() != null && game.getGenre().equalsIgnoreCase(genre.trim());
    }

    private boolean matchesPlatform(Game game, String platform) {
        if (platform == null || platform.isBlank()) {
            return true;
        }

        return game.getPlatform() != null && game.getPlatform().equalsIgnoreCase(platform.trim());
    }
}