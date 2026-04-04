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
    public String games(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platform,
            Model model,
            Authentication authentication
    ) {
        User loggedUser = getLoggedUser(authentication);

        List<Game> games = gameService.searchGames(keyword, genre, platform);

        if (loggedUser != null) {
            for (Game game : games) {
                game.setFavorite(favoriteService.isFavorite(loggedUser, game));
            }
        }

        model.addAttribute("pageTitle", "Browse Games");
        model.addAttribute("games", games);
        model.addAttribute("keyword", keyword);
        model.addAttribute("genre", genre);
        model.addAttribute("platform", platform);
        model.addAttribute("loggedUser", loggedUser);

        return "games";
    }

    @GetMapping("/games/{id}")
    public String gameDetails(@PathVariable Long id, Model model, Authentication authentication) {
        User loggedUser = getLoggedUser(authentication);

        Game game = gameService.getGameById(id);
        if (game == null) {
            return "redirect:/games";
        }

        if (loggedUser != null) {
            game.setFavorite(favoriteService.isFavorite(loggedUser, game));
        }

        List<Game> relatedGames = gameService.getRelatedGames(id);

        if (loggedUser != null) {
            for (Game relatedGame : relatedGames) {
                relatedGame.setFavorite(favoriteService.isFavorite(loggedUser, relatedGame));
            }
        }

        model.addAttribute("game", game);
        model.addAttribute("relatedGames", relatedGames);
        model.addAttribute("reviews", reviewService.getReviewsByGame(game));
        model.addAttribute("loggedUser", loggedUser);

        return "game-details";
    }

    @GetMapping("/library")
    public String library(Model model, Authentication authentication) {
        User loggedUser = getLoggedUser(authentication);

        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Game> libraryGames = favoriteService.getFavoriteGames(loggedUser);

        for (Game game : libraryGames) {
            game.setFavorite(true);
        }

        model.addAttribute("libraryGames", libraryGames);
        model.addAttribute("loggedUser", loggedUser);

        return "library";
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