package com.dulith.gamehub.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.FavoriteService;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.ReviewService;
import com.dulith.gamehub.service.UserService;

@Controller
public class GameController {

    private final GameService gameService;
    private final UserService userService;
    private final FavoriteService favoriteService;
    private final ReviewService reviewService;

    public GameController(GameService gameService,
                          UserService userService,
                          FavoriteService favoriteService,
                          ReviewService reviewService) {
        this.gameService = gameService;
        this.userService = userService;
        this.favoriteService = favoriteService;
        this.reviewService = reviewService;
    }

    @GetMapping("/games")
    public String games(@RequestParam(required = false) String genre,
                        @RequestParam(required = false) String platform,
                        @RequestParam(required = false) String search,
                        Authentication authentication,
                        Model model) {

        User loggedUser = getLoggedUser(authentication);
        List<Game> games = gameService.getAllGames();

        if (genre != null && !genre.isBlank()) {
            games = games.stream()
                    .filter(game -> game.getGenre() != null && game.getGenre().equalsIgnoreCase(genre))
                    .toList();
        }

        if (platform != null && !platform.isBlank()) {
            games = games.stream()
                    .filter(game -> game.getPlatform() != null && game.getPlatform().equalsIgnoreCase(platform))
                    .toList();
        }

        if (search != null && !search.isBlank()) {
            String keyword = search.toLowerCase();
            games = games.stream()
                    .filter(game ->
                            (game.getTitle() != null && game.getTitle().toLowerCase().contains(keyword)) ||
                            (game.getGenre() != null && game.getGenre().toLowerCase().contains(keyword)) ||
                            (game.getPlatform() != null && game.getPlatform().toLowerCase().contains(keyword)))
                    .toList();
        }

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("games", games);
        model.addAttribute("selectedGenre", genre);
        model.addAttribute("selectedPlatform", platform);
        model.addAttribute("search", search);

        model.addAttribute("genres", List.of(
                "Action RPG", "Adventure", "Shooter", "Racing", "Indie Platformer"
        ));

        model.addAttribute("platforms", List.of(
                "PC", "PlayStation 5", "Xbox Series X", "Nintendo Switch"
        ));

        return "games";
    }

    @GetMapping("/games/{id}")
    public String gameDetails(@PathVariable Long id,
                              Authentication authentication,
                              Model model) {
        User loggedUser = getLoggedUser(authentication);
        Game game = gameService.getGameById(id);

        if (game == null) {
            return "redirect:/games";
        }

        boolean isFavorite = false;
        if (loggedUser != null) {
            isFavorite = favoriteService.isFavorite(loggedUser, game);
        }

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("game", game);
        model.addAttribute("isFavorite", isFavorite);
        model.addAttribute("reviews", reviewService.getReviewsByGame(game));
        model.addAttribute("relatedGames", gameService.getRelatedGames(id));

        return "game-details";
    }

    private User getLoggedUser(Authentication authentication) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {
            return userService.findByEmail(authentication.getName());
        }
        return null;
    }
}