package com.dulith.gamehub.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.service.CartService;
import com.dulith.gamehub.service.UserService;

@Controller
public class CheckoutController {

    private final CartService cartService;
    private final UserService userService;

    public CheckoutController(CartService cartService, UserService userService) {
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName());

        if (user == null) {
            return "redirect:/login";
        }

        if (cartService.getCart(user).isEmpty()) {
            return "redirect:/cart";
        }

        model.addAttribute("loggedUser", user);
        model.addAttribute("cartItems", cartService.getCart(user));
        model.addAttribute("cartTotal", cartService.getCartTotal(user));
        model.addAttribute("activePage", "cart");

        return "checkout";
    }

    @PostMapping("/checkout/complete")
    public String completeCheckout(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(principal.getName());

        if (user == null) {
            return "redirect:/login";
        }

        double finalTotal = cartService.getCartTotal(user);

        cartService.clearCart(user);

        model.addAttribute("loggedUser", user);
        model.addAttribute("finalTotal", finalTotal);
        model.addAttribute("activePage", "cart");

        return "checkout-success";
    }
}