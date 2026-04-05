package com.dulith.gamehub.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.FavoriteService;
import com.dulith.gamehub.service.UserService;

@Controller
public class LibraryController {

    private final FavoriteService favoriteService;
    private final UserService userService;

    public LibraryController(FavoriteService favoriteService, UserService userService) {
        this.favoriteService = favoriteService;
        this.userService = userService;
    }

    @GetMapping("/library")
    public String library(Authentication authentication, Model model) {
        User loggedUser = getLoggedUser(authentication);

        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Game> libraryGames = favoriteService.getFavoriteGames(loggedUser);

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("libraryGames", libraryGames);

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