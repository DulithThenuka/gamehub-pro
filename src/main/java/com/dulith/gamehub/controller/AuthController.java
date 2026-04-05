package com.dulith.gamehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String fullName,
                           @RequestParam String email,
                           @RequestParam String password,
                           Model model) {

        try {
            if (fullName == null || fullName.trim().length() < 3) {
                model.addAttribute("error", "Full name must be at least 3 characters.");
                return "register";
            }

            if (email == null || email.trim().isEmpty()) {
                model.addAttribute("error", "Email is required.");
                return "register";
            }

            if (password == null || password.length() < 6) {
                model.addAttribute("error", "Password must be at least 6 characters.");
                return "register";
            }

            userService.registerUser(fullName, email, password);
            return "redirect:/login?registered=true";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Registration failed. Please try again.");
            return "register";
        }
    }
}