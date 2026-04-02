package com.dulith.gamehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "GameHub Pro");
        model.addAttribute("subtitle", "Discover, track, and explore your favorite games.");
        return "home";
    }
}