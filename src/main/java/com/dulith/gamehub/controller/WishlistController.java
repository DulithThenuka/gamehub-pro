package com.dulith.gamehub.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.UserService;
import com.dulith.gamehub.service.WishlistService;

@Controller
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final GameService gameService;

    public WishlistController(WishlistService wishlistService,
                              UserService userService,
                              GameService gameService) {
        this.wishlistService = wishlistService;
        this.userService = userService;
        this.gameService = gameService;
    }

    @PostMapping("/wishlist/toggle/{gameId}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> toggleWishlist(@PathVariable Long gameId,
                                                              Principal principal) {
        Map<String, Object> response = new HashMap<>();

        if (principal == null) {
            response.put("error", "not_logged_in");
            response.put("wishlisted", false);
            return ResponseEntity.ok(response);
        }

        User user = userService.findByEmail(principal.getName());
        Game game = gameService.getGameById(gameId);

        if (user == null || game == null) {
            response.put("error", "not_found");
            response.put("wishlisted", false);
            return ResponseEntity.ok(response);
        }

        boolean isWishlisted = wishlistService.toggleWishlist(user, game);

        response.put("wishlisted", isWishlisted);
        return ResponseEntity.ok(response);
    }
}