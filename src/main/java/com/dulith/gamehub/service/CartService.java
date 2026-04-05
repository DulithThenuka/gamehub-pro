package com.dulith.gamehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dulith.gamehub.entity.CartItem;
import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.repository.CartItemRepository;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> getCartItemsByUser(User user) {
        return cartItemRepository.findByUser(user);
    }

    public List<CartItem> getCart(User user) {
        return cartItemRepository.findByUser(user);
    }

    public void addToCart(User user, Game game) {
        CartItem existingItem = cartItemRepository.findByUserAndGame(user, game);

        if (existingItem != null) {
            int currentQty = existingItem.getQuantity();
            existingItem.setQuantity(currentQty + 1);
            cartItemRepository.save(existingItem);
            return;
        }

        CartItem cartItem = new CartItem();
        cartItem.setUser(user);
        cartItem.setGame(game);
        cartItem.setQuantity(1);

        cartItemRepository.save(cartItem);
    }

    public void removeCartItemById(Long cartItemId, User user) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElse(null);

        if (cartItem == null) {
            return;
        }

        if (cartItem.getUser() == null || user == null) {
            return;
        }

        if (!cartItem.getUser().getId().equals(user.getId())) {
            return;
        }

        cartItemRepository.delete(cartItem);
    }

    public void clearCart(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        cartItemRepository.deleteAll(cartItems);
    }

    public int getCartCount(User user) {
        return cartItemRepository.findByUser(user).size();
    }

    public double getCartTotal(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        double total = 0.0;

        for (CartItem item : cartItems) {
            if (item.getGame() == null) {
                continue;
            }

            double price = item.getGame().getDiscountedPrice() != null
                    ? item.getGame().getDiscountedPrice()
                    : item.getGame().getPrice();

            total += price * item.getQuantity();
        }

        return total;
    }
}