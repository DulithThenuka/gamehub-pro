package com.dulith.gamehub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.FavoriteService;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.UserService;

@Controller
public class HomeController {

    private final UserService userService;
    private final GameService gameService;
    private final FavoriteService favoriteService;

    public HomeController(UserService userService, GameService gameService, FavoriteService favoriteService) {
        this.userService = userService;
        this.gameService = gameService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/")
    public String home(Model model, Authentication authentication) {
        User loggedUser = null;
        int favoriteCount = 0;

        if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getName())) {
            loggedUser = userService.findByEmail(authentication.getName());
            if (loggedUser != null) {
                favoriteCount = favoriteService.getFavoriteGames(loggedUser).size();
            }
        }

        model.addAttribute("title", "GameHub Pro");
        model.addAttribute("subtitle", "Discover, track, and explore your favorite games.");
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("favoriteCount", favoriteCount);
        model.addAttribute("totalGames", gameService.countGames());

        return "home";
    }
}