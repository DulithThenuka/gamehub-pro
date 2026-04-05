package com.dulith.gamehub.controller;

import java.time.LocalDate;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.News;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.NewsService;
import com.dulith.gamehub.service.UserService;

@Controller
@RequestMapping("/admin/news")
public class AdminNewsController {

    private final NewsService newsService;
    private final UserService userService;

    public AdminNewsController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping
    public String adminNewsPage(Model model, Authentication authentication) {
        User loggedUser = getLoggedUser(authentication);

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("newsList", newsService.getAllNews());

        return "admin-news";
    }

    @GetMapping("/add")
    public String addNewsPage(Model model, Authentication authentication) {
        User loggedUser = getLoggedUser(authentication);

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("news", new News());

        return "admin-news-form";
    }

    @PostMapping("/save")
    public String saveNews(@RequestParam String title,
                           @RequestParam String summary,
                           @RequestParam String content,
                           @RequestParam String imageUrl,
                           @RequestParam String category,
                           @RequestParam String readTime,
                           @RequestParam(required = false) boolean featured) {

        News news = new News();
        news.setTitle(title);
        news.setSummary(summary);
        news.setContent(content);
        news.setImageUrl(imageUrl);
        news.setCategory(category);
        news.setReadTime(readTime);
        news.setFeatured(featured);
        news.setPublishedDate(LocalDate.now());

        newsService.save(news);

        return "redirect:/admin/news";
    }

    @PostMapping("/delete/{id}")
    public String deleteNews(@PathVariable Long id) {
        newsService.deleteById(id);
        return "redirect:/admin/news";
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