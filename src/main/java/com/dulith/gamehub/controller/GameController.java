package com.dulith.gamehub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.service.GameService;

@Controller
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/games")
    public String games(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String genre,
            @RequestParam(required = false) String platform,
            Model model
    ) {
        model.addAttribute("pageTitle", "Browse Games");
        model.addAttribute("games", gameService.searchGames(keyword, genre, platform));
        model.addAttribute("keyword", keyword);
        model.addAttribute("genre", genre);
        model.addAttribute("platform", platform);
        return "games";
    }

    @GetMapping("/games/{id}")
    public String gameDetails(@PathVariable Long id, Model model) {
        Game game = gameService.getGameById(id);

        if (game == null) {
            return "redirect:/games";
        }

        model.addAttribute("game", game);
        model.addAttribute("relatedGames", gameService.getRelatedGames(id));
        return "game-details";
    }
}