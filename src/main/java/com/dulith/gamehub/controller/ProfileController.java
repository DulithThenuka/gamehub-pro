package com.dulith.gamehub.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.CartService;
import com.dulith.gamehub.service.UserService;
import com.dulith.gamehub.service.WishlistService;

@Controller
public class ProfileController {

    private final UserService userService;
    private final WishlistService wishlistService;
    private final CartService cartService;

    public ProfileController(UserService userService,
                             WishlistService wishlistService,
                             CartService cartService) {
        this.userService = userService;
        this.wishlistService = wishlistService;
        this.cartService = cartService;
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(principal.getName());

        if (loggedUser == null) {
            return "redirect:/login";
        }

        int wishlistCount = wishlistService.getWishlistGames(loggedUser).size();
        int cartCount = cartService.getCartItemsByUser(loggedUser).size();

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("wishlistCount", wishlistCount);
        model.addAttribute("libraryCount", 0); // until real purchases system is added
        model.addAttribute("cartCount", cartCount);
        model.addAttribute("activePage", "profile");

        return "profile";
    }
}