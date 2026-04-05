package com.dulith.gamehub.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.service.GameService;
import com.dulith.gamehub.service.UserService;

@Component
public class DataLoader implements CommandLineRunner {

    private final GameService gameService;
    private final UserService userService;

    public DataLoader(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @Override
    public void run(String... args) {
        userService.createAdminIfNotExists();

        if (gameService.countGames() == 0) {

            Game game1 = new Game();
            game1.setTitle("Cyber Realm");
            game1.setGenre("Action RPG");
            game1.setPlatform("PC");
            game1.setRating(4.8);
            game1.setDescription("A futuristic action role-playing game...");
            game1.setImageUrl("https://images.unsplash.com/photo-1542751371-adc38448a05e");
            game1.setTrailerUrl("https://www.youtube.com/embed/dQw4w9WgXcQ");
            game1.setPrice(59.99);
            game1.setDiscountPercent(20);
            game1.setDiscountedPrice(47.99);
            gameService.saveGame(game1);

            Game game2 = new Game();
            game2.setTitle("Shadow Drift");
            game2.setGenre("Racing");
            game2.setPlatform("PlayStation 5");
            game2.setRating(4.5);
            game2.setDescription("A high-speed street racing experience...");
            game2.setImageUrl("https://images.unsplash.com/photo-1511512578047-dfb367046420");
            game2.setTrailerUrl("https://www.youtube.com/embed/dQw4w9WgXcQ");
            game2.setPrice(39.99);
            game2.setDiscountPercent(10);
            game2.setDiscountedPrice(35.99);
            gameService.saveGame(game2);

            Game game3 = new Game();
            game3.setTitle("Kingdom Eclipse");
            game3.setGenre("Adventure");
            game3.setPlatform("Xbox Series X");
            game3.setRating(4.7);
            game3.setDescription("Explore a vast fantasy kingdom...");
            game3.setImageUrl("https://images.unsplash.com/photo-1493711662062-fa541adb3fc8");
            game3.setTrailerUrl("https://www.youtube.com/embed/dQw4w9WgXcQ");
            game3.setPrice(49.99);
            game3.setDiscountPercent(25);
            game3.setDiscountedPrice(37.49);
            gameService.saveGame(game3);

            Game game4 = new Game();
            game4.setTitle("Battle Zone X");
            game4.setGenre("Shooter");
            game4.setPlatform("PC");
            game4.setRating(4.4);
            game4.setDescription("Join fast-paced multiplayer combat...");
            game4.setImageUrl("https://images.unsplash.com/photo-1550745165-9bc0b252726f");
            game4.setTrailerUrl("https://www.youtube.com/embed/dQw4w9WgXcQ");
            game4.setPrice(29.99);
            game4.setDiscountPercent(0);
            game4.setDiscountedPrice(29.99);
            gameService.saveGame(game4);

            Game game5 = new Game();
            game5.setTitle("Pixel Odyssey");
            game5.setGenre("Indie Platformer");
            game5.setPlatform("Nintendo Switch");
            game5.setRating(4.6);
            game5.setDescription("A beautiful pixel-art journey...");
            game5.setImageUrl("https://images.unsplash.com/photo-1534423861386-85a16f5d13fd");
            game5.setTrailerUrl("https://www.youtube.com/embed/dQw4w9WgXcQ");
            game5.setPrice(19.99);
            game5.setDiscountPercent(50);
            game5.setDiscountedPrice(9.99);
            gameService.saveGame(game5);

            System.out.println("✅ Sample games inserted successfully.");
        }
    }
}