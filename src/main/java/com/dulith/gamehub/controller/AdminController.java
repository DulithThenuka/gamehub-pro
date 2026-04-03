package com.dulith.gamehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.service.GameService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final GameService gameService;

    public AdminController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping
    public String adminDashboard(Model model) {
        model.addAttribute("games", gameService.getAllGames());
        model.addAttribute("totalGames", gameService.countGames());
        return "admin/dashboard";
    }

    @GetMapping("/games/new")
    public String newGameForm(Model model) {
        model.addAttribute("game", new Game());
        model.addAttribute("formTitle", "Add New Game");
        model.addAttribute("formAction", "/admin/games/save");
        return "admin/game-form";
    }

    @GetMapping("/games/edit/{id}")
    public String editGameForm(@PathVariable Long id, Model model) {
        Game game = gameService.getGameById(id);

        if (game == null) {
            return "redirect:/admin";
        }

        model.addAttribute("game", game);
        model.addAttribute("formTitle", "Edit Game");
        model.addAttribute("formAction", "/admin/games/save");
        return "admin/game-form";
    }

    @PostMapping("/games/save")
    public String saveGame(
            @RequestParam(required = false) Long id,
            @RequestParam String title,
            @RequestParam String genre,
            @RequestParam String platform,
            @RequestParam Double rating,
            @RequestParam String description,
            @RequestParam String imageUrl,
            @RequestParam String trailerUrl
    ) {
        Game game = new Game(id, title, genre, platform, rating, description, imageUrl, trailerUrl);
        gameService.saveGame(game);
        return "redirect:/admin";
    }

    @PostMapping("/games/delete/{id}")
    public String deleteGame(@PathVariable Long id) {
        gameService.deleteGameById(id);
        return "redirect:/admin";
    }
}