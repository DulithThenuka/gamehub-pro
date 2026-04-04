package com.dulith.gamehub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dulith.gamehub.entity.CartItem;
import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.entity.User;
import com.dulith.gamehub.repository.CartRepository;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(User user, Game game) {
        Optional<CartItem> existing = cartRepository.findByUserAndGame(user, game);

        if (existing.isPresent()) {
            CartItem item = existing.get();
            item.setQuantity(item.getQuantity() + 1);
            cartRepository.save(item);
        } else {
            CartItem item = new CartItem();
            item.setUser(user);
            item.setGame(game);
            item.setQuantity(1);
            cartRepository.save(item);
        }
    }

    public List<CartItem> getCart(User user) {
        return cartRepository.findByUser(user);
    }

    public void removeFromCart(Long cartItemId) {
        cartRepository.deleteById(cartItemId);
    }

    public double getCartTotal(User user) {
        return getCart(user).stream()
                .mapToDouble(item -> item.getGame().getDiscountedPrice() * item.getQuantity())
                .sum();
    }

    public void clearCart(User user) {
        cartRepository.deleteAll(cartRepository.findByUser(user));
    }
}