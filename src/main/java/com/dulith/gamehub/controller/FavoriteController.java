package com.dulith.gamehub.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.FavoriteService;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.UserService;

@Controller
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final GameService gameService;
    private final UserService userService;

    public FavoriteController(FavoriteService favoriteService,
                              GameService gameService,
                              UserService userService) {
        this.favoriteService = favoriteService;
        this.gameService = gameService;
        this.userService = userService;
    }

    @PostMapping("/favorites/toggle")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> toggleFavorite(@RequestParam Long gameId,
                                                              Principal principal) {
        Map<String, Object> response = new HashMap<>();

        if (principal == null) {
            response.put("error", "not_logged_in");
            response.put("favorited", false);
            return ResponseEntity.ok(response);
        }

        User user = userService.findByEmail(principal.getName());
        Game game = gameService.getGameById(gameId);

        if (user == null || game == null) {
            response.put("error", "not_found");
            response.put("favorited", false);
            return ResponseEntity.ok(response);
        }

        boolean isFavorited = favoriteService.toggleFavorite(user, game);

        response.put("favorited", isFavorited);
        return ResponseEntity.ok(response);
    }
}