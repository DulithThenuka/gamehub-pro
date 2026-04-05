package com.dulith.gamehub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.UserService;
import com.dulith.gamehub.service.WishlistService;

@Controller
public class LibraryController {

    private final UserService userService;
    private final WishlistService wishlistService;

    public LibraryController(UserService userService, WishlistService wishlistService) {
        this.userService = userService;
        this.wishlistService = wishlistService;
    }

    @GetMapping("/library")
    public String library(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(principal.getName());

        if (loggedUser == null) {
            return "redirect:/login";
        }

        // Temporary fallback until you build a real purchases/orders table.
        List<Game> libraryGames = wishlistService.getWishlistGames(loggedUser);

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("libraryGames", libraryGames);
        model.addAttribute("activePage", "library");

        return "library";
    }
}