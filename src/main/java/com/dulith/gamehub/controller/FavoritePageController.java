package com.dulith.gamehub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.FavoriteService;
import com.dulith.gamehub.service.UserService;

@Controller
public class FavoritePageController {

    private final FavoriteService favoriteService;
    private final UserService userService;

    public FavoritePageController(FavoriteService favoriteService,
                                  UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    @GetMapping("/favorites")
    public String favoritesPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName());
        List<Game> favoriteGames = favoriteService.getFavoriteGames(user);

        model.addAttribute("loggedUser", user);
        model.addAttribute("favoriteGames", favoriteGames);

        return "favorites";
    }
}