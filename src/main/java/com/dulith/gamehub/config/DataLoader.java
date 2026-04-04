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

            gameService.saveGame(new Game(
                    "Cyber Realm",
                    "Action RPG",
                    "PC",
                    4.8,
                    "A futuristic action role-playing game...",
                    "https://images.unsplash.com/photo-1542751371-adc38448a05e",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ",
                    59.99,
                    20,
                    47.99
            ));

            gameService.saveGame(new Game(
                    "Shadow Drift",
                    "Racing",
                    "PlayStation 5",
                    4.5,
                    "A high-speed street racing experience...",
                    "https://images.unsplash.com/photo-1511512578047-dfb367046420",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ",
                    39.99,
                    10,
                    35.99
            ));

            gameService.saveGame(new Game(
                    "Kingdom Eclipse",
                    "Adventure",
                    "Xbox Series X",
                    4.7,
                    "Explore a vast fantasy kingdom...",
                    "https://images.unsplash.com/photo-1493711662062-fa541adb3fc8",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ",
                    49.99,
                    25,
                    37.49
            ));

            gameService.saveGame(new Game(
                    "Battle Zone X",
                    "Shooter",
                    "PC",
                    4.4,
                    "Join fast-paced multiplayer combat...",
                    "https://images.unsplash.com/photo-1550745165-9bc0b252726f",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ",
                    29.99,
                    0,
                    29.99
            ));

            gameService.saveGame(new Game(
                    "Pixel Odyssey",
                    "Indie Platformer",
                    "Nintendo Switch",
                    4.6,
                    "A beautiful pixel-art journey...",
                    "https://images.unsplash.com/photo-1534423861386-85a16f5d13fd",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ",
                    19.99,
                    50,
                    9.99
            ));

            System.out.println("✅ Sample games inserted successfully.");
        }
    }
}