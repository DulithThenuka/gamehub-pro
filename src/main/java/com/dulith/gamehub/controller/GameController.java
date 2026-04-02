package com.dulith.gamehub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.FavoriteService;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.UserService;

@Controller
public class GameController {

    private final GameService gameService;
    private final UserService userService;
    private final FavoriteService favoriteService;

    public GameController(GameService gameService, UserService userService, FavoriteService favoriteService) {
        this.gameService = gameService;
        this.userService = userService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/games")
    public String games(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platform,
            Model model,
            Authentication authentication
    ) {
        User loggedUser = null;

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            loggedUser = userService.findByEmail(authentication.getName());
        }

        model.addAttribute("pageTitle", "Browse Games");
        model.addAttribute("games", gameService.searchGames(keyword, genre, platform));
        model.addAttribute("keyword", keyword);
        model.addAttribute("genre", genre);
        model.addAttribute("platform", platform);
        model.addAttribute("loggedUser", loggedUser);
        return "games";
    }

    @GetMapping("/games/{id}")
    public String gameDetails(@PathVariable Long id, Model model, Authentication authentication) {
        Game game = gameService.getGameById(id);

        if (game == null) {
            return "redirect:/games";
        }

        User loggedUser = null;
        boolean favorite = false;

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            loggedUser = userService.findByEmail(authentication.getName());
            if (loggedUser != null) {
                favorite = favoriteService.isFavorite(loggedUser, game);
            }
        }

        model.addAttribute("game", game);
        model.addAttribute("relatedGames", gameService.getRelatedGames(id));
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("isFavorite", favorite);
        return "game-details";
    }

    @PostMapping("/favorites/add/{gameId}")
    public String addFavorite(@PathVariable Long gameId, Authentication authentication) {
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.getGameById(gameId);

        if (user != null && game != null) {
            favoriteService.addFavorite(user, game);
        }

        return "redirect:/games/" + gameId;
    }

    @PostMapping("/favorites/remove/{gameId}")
    public String removeFavorite(@PathVariable Long gameId, Authentication authentication) {
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName());
        Game game = gameService.getGameById(gameId);

        if (user != null && game != null) {
            favoriteService.removeFavorite(user, game);
        }

        return "redirect:/games/" + gameId;
    }

    @GetMapping("/library")
    public String library(Model model, Authentication authentication) {
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName());

        model.addAttribute("pageTitle", "My Library");
        model.addAttribute("loggedUser", user);
        model.addAttribute("games", favoriteService.getFavoriteGames(user));
        return "library";
    }
}