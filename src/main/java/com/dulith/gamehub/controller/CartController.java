package com.dulith.gamehub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dulith.gamehub.entity.CartItem;
import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.CartService;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.UserService;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserService userService;
    private final GameService gameService;

    public CartController(CartService cartService,
                          UserService userService,
                          GameService gameService) {
        this.cartService = cartService;
        this.userService = userService;
        this.gameService = gameService;
    }

    @PostMapping("/add/{gameId}")
    public String addToCart(@PathVariable Long gameId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName());
        Game game = gameService.getGameById(gameId);

        if (user != null && game != null) {
            cartService.addToCart(user, game);
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        cartService.removeFromCart(cartItemId);
        return "redirect:/cart";
    }

    @GetMapping
    public String viewCart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName());
        List<CartItem> cartItems = cartService.getCart(user);

        model.addAttribute("loggedUser", user);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartService.getCartTotal(user));

        return "cart";
    }
}