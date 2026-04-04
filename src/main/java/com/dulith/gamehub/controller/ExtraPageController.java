package com.dulith.gamehub.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.UserService;

@Controller
public class ExtraPageController {

    private final UserService userService;

    public ExtraPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/gifts")
    public String gifts(Authentication authentication, Model model) {
        if (authentication != null) {
            User loggedUser = userService.findByEmail(authentication.getName());
            model.addAttribute("loggedUser", loggedUser);
        }
        return "gifts";
    }
}