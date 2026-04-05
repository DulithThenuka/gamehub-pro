package com.dulith.gamehub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.FavoriteService;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.UserService;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final GameService gameService;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteService,
                              GameService gameService,
                              UserService userService) {
        this.favoriteService = favoriteService;
        this.gameService = gameService;
        this.userService = userService;
    }

    @PostMapping("/favorites/toggle")
    public String toggleFavorite(@RequestParam Long gameId, Authentication authentication) {
        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(authentication.getName());
        Game game = gameService.getGameById(gameId);

        if (loggedUser == null || game == null) {
            return "redirect:/games";
        }

        if (favoriteService.isFavorite(loggedUser, game)) {
            favoriteService.removeFromFavorites(loggedUser, game);
        } else {
            favoriteService.addToFavorites(loggedUser, game);
        }

        return "redirect:/games/" + gameId;
    }
}