package com.dulith.gamehub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.News;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.NewsService;
import com.dulith.gamehub.service.UserService;

@Controller
public class NewsController {

    private final NewsService newsService;
    private final UserService userService;

    public NewsController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping("/news")
    public String news(@RequestParam(required = false) String category,
                       Model model,
                       Principal principal) {

        User loggedUser = null;

        if (principal != null) {
            loggedUser = userService.findByEmail(principal.getName());
        }

        List<News> newsList = newsService.getNewsByCategory(category);
        List<News> featuredNews = newsService.getFeaturedNews();
        List<String> categories = newsService.getAllCategories();

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("newsList", newsList);
        model.addAttribute("featuredNews", featuredNews);
        model.addAttribute("categories", categories);
        model.addAttribute("selectedCategory", category != null ? category : "");
        model.addAttribute("activePage", "news");

        return "news";
    }
}