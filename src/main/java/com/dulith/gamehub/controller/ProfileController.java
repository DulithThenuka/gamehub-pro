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
public class ProfileController {

    private final UserService userService;
    private final FavoriteService favoriteService;
    private final GameService gameService;

    public ProfileController(UserService userService, FavoriteService favoriteService, GameService gameService) {
        this.userService = userService;
        this.favoriteService = favoriteService;
        this.gameService = gameService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Authentication authentication) {
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(authentication.getName());

        model.addAttribute("user", user);
        model.addAttribute("favoriteCount", favoriteService.getFavoriteGames(user).size());
        model.addAttribute("totalGames", gameService.countGames());

        return "profile";
    }
}