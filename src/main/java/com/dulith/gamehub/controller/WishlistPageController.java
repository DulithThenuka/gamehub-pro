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
public class WishlistPageController {

    private final WishlistService wishlistService;
    private final UserService userService;

    public WishlistPageController(WishlistService wishlistService,
                                  UserService userService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
    }

    @GetMapping("/wishlist")
    public String showWishlistPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(principal.getName());

        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Game> wishlistGames = wishlistService.getWishlistGames(loggedUser);

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("wishlistGames", wishlistGames);
        model.addAttribute("activePage", "wishlist");

        return "wishlist";
    }
}