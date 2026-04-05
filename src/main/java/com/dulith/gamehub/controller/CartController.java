package com.dulith.gamehub.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.dulith.gamehub.entity.CartItem;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.CartService;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.UserService;

@Controller
public class CartController {

    private final CartService cartService;
    private final GameService gameService;
    private final UserService userService;

    public CartController(CartService cartService,
                          GameService gameService,
                          UserService userService) {
        this.cartService = cartService;
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping("/cart")
    public String cart(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(principal.getName());

        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<CartItem> cartItems = cartService.getCartItemsByUser(loggedUser);

        double cartTotal = 0.0;
        double finalTotal = 0.0;

        for (CartItem item : cartItems) {
            if (item.getGame() == null) {
                continue;
            }

            double basePrice = item.getGame().getPrice() != null ? item.getGame().getPrice() : 0.0;
            double discountedPrice = item.getGame().getDiscountedPrice() != null
                    ? item.getGame().getDiscountedPrice()
                    : basePrice;

            int quantity = item.getQuantity() > 0 ? item.getQuantity() : 1;

            cartTotal += basePrice * quantity;
            finalTotal += discountedPrice * quantity;
        }

        double discountTotal = cartTotal - finalTotal;

        model.addAttribute("loggedUser", loggedUser);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("cartTotal", cartTotal);
        model.addAttribute("discountTotal", discountTotal);
        model.addAttribute("finalTotal", finalTotal);
        model.addAttribute("activePage", "cart");

        return "cart";
    }

    @PostMapping("/cart/add/{gameId}")
    public String addToCart(@PathVariable Long gameId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(principal.getName());

        if (loggedUser == null) {
            return "redirect:/login";
        }

        var game = gameService.getGameById(gameId);

        if (game != null) {
            cartService.addToCart(loggedUser, game);
        }

        return "redirect:/cart";
    }

    @PostMapping("/cart/remove/{cartItemId}")
    public String removeFromCart(@PathVariable Long cartItemId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User loggedUser = userService.findByEmail(principal.getName());

        if (loggedUser == null) {
            return "redirect:/login";
        }

        cartService.removeCartItemById(cartItemId, loggedUser);

        return "redirect:/cart";
    }
}