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
        User loggedUser = getLoggedUser(authentication);
        if (loggedUser == null) {
            return "redirect:/login";
        }

        loadProfileData(model, loggedUser);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String fullName,
                                Authentication authentication,
                                Model model) {
        User loggedUser = getLoggedUser(authentication);
        if (loggedUser == null) {
            return "redirect:/login";
        }

        try {
            loggedUser.setFullName(fullName.trim());
            userService.updateProfile(loggedUser);

            loadProfileData(model, loggedUser);
            model.addAttribute("success", "Profile updated successfully.");
            return "profile";

        } catch (Exception e) {
            e.printStackTrace();
            loadProfileData(model, loggedUser);
            model.addAttribute("error", "Failed to update profile.");
            return "profile";
        }
    }

    private User getLoggedUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            return null;
        }
        return userService.findByEmail(authentication.getName());
    }

    private void loadProfileData(Model model, User loggedUser) {
        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("favoriteCount", favoriteService.getFavoriteGames(loggedUser).size());
        model.addAttribute("libraryCount", favoriteService.getFavoriteGames(loggedUser).size());
        model.addAttribute("orderCount", 0);
    }
}