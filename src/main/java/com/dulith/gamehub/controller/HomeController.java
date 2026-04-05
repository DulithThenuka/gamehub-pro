package com.dulith.gamehub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.News;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.NewsService;
import com.dulith.gamehub.service.UserService;

@Controller
public class HomeController {

    private final GameService gameService;
    private final NewsService newsService;
    private final UserService userService;

    public HomeController(GameService gameService,
                          NewsService newsService,
                          UserService userService) {
        this.gameService = gameService;
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String home(Model model, Principal principal) {
        User loggedUser = null;

        if (principal != null) {
            loggedUser = userService.findByEmail(principal.getName());
        }

        List<Game> allGames = gameService.getAllGames();
        List<News> latestNews = newsService.getLatestNews();

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("featuredGames", gameService.getFeaturedGames());
        model.addAttribute("newReleases", gameService.getNewReleases());
        model.addAttribute("topSellers", gameService.getTopSellers());
        model.addAttribute("freeToPlay", gameService.getFreeToPlayGames());
        model.addAttribute("latestNews", latestNews);

        // Optional fallback so the page never feels empty
        model.addAttribute("allGames", allGames);
        model.addAttribute("activePage", "store");

        return "home";
    }
}