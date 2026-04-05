package com.dulith.gamehub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.ReviewService;
import com.dulith.gamehub.service.UserService;
import com.dulith.gamehub.service.WishlistService;

@Controller
public class GameController {

    private final GameService gameService;
    private final ReviewService reviewService;
    private final UserService userService;
    private final WishlistService wishlistService;

    public GameController(GameService gameService,
                          ReviewService reviewService,
                          UserService userService,
                          WishlistService wishlistService) {
        this.gameService = gameService;
        this.reviewService = reviewService;
        this.userService = userService;
        this.wishlistService = wishlistService;
    }

    @GetMapping("/games")
    public String games(@RequestParam(required = false) String search,
                        @RequestParam(required = false) String genre,
                        @RequestParam(required = false) String platform,
                        Model model,
                        Principal principal) {

        User loggedUser = null;

        if (principal != null) {
            loggedUser = userService.findByEmail(principal.getName());
        }

        List<Game> games = gameService.searchGames(search, genre, platform);

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("games", games);
        model.addAttribute("genres", gameService.getAllGenres());
        model.addAttribute("platforms", gameService.getAllPlatforms());
        model.addAttribute("search", search != null ? search : "");
        model.addAttribute("selectedGenre", genre != null ? genre : "");
        model.addAttribute("selectedPlatform", platform != null ? platform : "");
        model.addAttribute("activePage", "games");

        return "games";
    }

    @GetMapping("/games/{id}")
public String gameDetails(@PathVariable Long id, Model model, Principal principal) {
    Game game = gameService.getGameById(id);

    if (game == null) {
        return "redirect:/games";
    }

    User loggedUser = null;
    boolean isWishlisted = false;

    if (principal != null) {
        loggedUser = userService.findByEmail(principal.getName());

        if (loggedUser != null) {
            isWishlisted = wishlistService.isInWishlist(loggedUser, game);
        }
    }

    List<Game> relatedGames = gameService.getRelatedGames(id);

    model.addAttribute("loggedUser", loggedUser);
    model.addAttribute("game", game);
    model.addAttribute("reviews", reviewService.getReviewsByGame(game));
    model.addAttribute("relatedGames", relatedGames);
    model.addAttribute("isWishlisted", isWishlisted);
    model.addAttribute("averageRating", reviewService.getAverageRating(game));
    model.addAttribute("reviewCount", reviewService.getReviewCount(game));
    model.addAttribute("userReview", loggedUser != null ? reviewService.getReviewByUserAndGame(loggedUser, game) : null);
    model.addAttribute("activePage", "games");

    return "game-details";
}
}