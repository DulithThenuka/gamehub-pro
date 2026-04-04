package com.dulith.gamehub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.FavoriteService;
import com.dulith.gamehub.service.UserService;

@Controller
public class ProfileController {

    private final UserService userService;
    private final FavoriteService favoriteService;

    public ProfileController(UserService userService, FavoriteService favoriteService) {
        this.userService = userService;
        this.favoriteService = favoriteService;
    }

    @GetMapping("/profile")
    public String profile(Authentication authentication, Model model) {
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(authentication.getName());

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("favoriteCount", favoriteService.getFavoriteGames(loggedUser).size());
        model.addAttribute("libraryCount", favoriteService.getFavoriteGames(loggedUser).size());
        model.addAttribute("orderCount", 0);

        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String fullName,
                                @RequestParam String email,
                                Authentication authentication,
                                Model model) {
        if (authentication == null || "anonymousUser".equals(authentication.getName())) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(authentication.getName());

        try {
            loggedUser.setFullName(fullName);
            loggedUser.setEmail(email);
            userService.save(loggedUser);

            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("favoriteCount", favoriteService.getFavoriteGames(loggedUser).size());
            model.addAttribute("libraryCount", favoriteService.getFavoriteGames(loggedUser).size());
            model.addAttribute("orderCount", 0);
            model.addAttribute("success", "Profile updated successfully.");

            return "profile";
        } catch (Exception e) {
            model.addAttribute("loggedUser", loggedUser);
            model.addAttribute("favoriteCount", favoriteService.getFavoriteGames(loggedUser).size());
            model.addAttribute("libraryCount", favoriteService.getFavoriteGames(loggedUser).size());
            model.addAttribute("orderCount", 0);
            model.addAttribute("error", "Failed to update profile.");

            return "profile";
        }
    }
}