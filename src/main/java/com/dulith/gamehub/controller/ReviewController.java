package com.dulith.gamehub.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.ReviewService;
import com.dulith.gamehub.service.UserService;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final GameService gameService;
    private final UserService userService;

    public ReviewController(ReviewService reviewService,
                            GameService gameService,
                            UserService userService) {
        this.reviewService = reviewService;
        this.gameService = gameService;
        this.userService = userService;
    }

    @PostMapping("/reviews/add/{gameId}")
    public String addReview(@PathVariable Long gameId,
                            @RequestParam Integer rating,
                            @RequestParam(required = false) String comment,
                            Principal principal) {

        if (principal == null) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(principal.getName());

        if (loggedUser == null) {
            return "redirect:/login";
        }

        Game game = gameService.getGameById(gameId);

        if (game == null) {
            return "redirect:/games";
        }

        if (rating < 1 || rating > 5) {
            return "redirect:/games/" + gameId;
        }

        reviewService.saveOrUpdateReview(loggedUser, game, rating, comment);

        return "redirect:/games/" + gameId;
    }
}