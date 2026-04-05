package com.dulith.gamehub.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String news(Model model, Authentication authentication) {
        User loggedUser = getLoggedUser(authentication);

        News featuredNews = newsService.getFeaturedNews();
        List<News> latestNews = newsService.getLatestNews();
        List<News> popularNews = newsService.getPopularNews();

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("featuredNews", featuredNews);
        model.addAttribute("latestNews", latestNews);
        model.addAttribute("popularNews", popularNews);

        return "news";
    }

    @GetMapping("/news/{id}")
    public String newsDetails(@PathVariable Long id, Model model, Authentication authentication) {
        User loggedUser = getLoggedUser(authentication);
        News article = newsService.getNewsById(id);

        if (article == null) {
            return "redirect:/news";
        }

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("article", article);
        model.addAttribute("latestNews", newsService.getLatestNews());

        return "news-details";
    }

    private User getLoggedUser(Authentication authentication) {
        if (authentication != null
                && authentication.isAuthenticated()
                && !"anonymousUser".equals(authentication.getName())) {
            return userService.findByEmail(authentication.getName());
        }
        return null;
    }
}