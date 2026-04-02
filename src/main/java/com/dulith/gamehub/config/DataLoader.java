package com.dulith.gamehub.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.dulith.gamehub.entity.Game;
import com.dulith.gamehub.service.GameService;

@Component
public class DataLoader implements CommandLineRunner {

    private final GameService gameService;

    public DataLoader(GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) {
        if (gameService.countGames() == 0) {

            gameService.saveGame(new Game(
                    null,
                    "Cyber Realm",
                    "Action RPG",
                    "PC",
                    4.8,
                    "A futuristic action role-playing game set in a neon cyber world filled with missions, upgrades, and online battles.",
                    "https://images.unsplash.com/photo-1542751371-adc38448a05e?auto=format&fit=crop&w=900&q=80",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ"
            ));

            gameService.saveGame(new Game(
                    null,
                    "Shadow Drift",
                    "Racing",
                    "PlayStation 5",
                    4.5,
                    "A high-speed street racing experience with dynamic weather, realistic cars, and intense night city tracks.",
                    "https://images.unsplash.com/photo-1511512578047-dfb367046420?auto=format&fit=crop&w=900&q=80",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ"
            ));

            gameService.saveGame(new Game(
                    null,
                    "Kingdom Eclipse",
                    "Adventure",
                    "Xbox Series X",
                    4.7,
                    "Explore a vast fantasy kingdom, fight legendary creatures, and uncover the secrets of an ancient eclipse.",
                    "https://images.unsplash.com/photo-1493711662062-fa541adb3fc8?auto=format&fit=crop&w=900&q=80",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ"
            ));

            gameService.saveGame(new Game(
                    null,
                    "Battle Zone X",
                    "Shooter",
                    "PC",
                    4.4,
                    "Join fast-paced multiplayer combat across futuristic war zones with tactical teams and heavy weapons.",
                    "https://images.unsplash.com/photo-1550745165-9bc0b252726f?auto=format&fit=crop&w=900&q=80",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ"
            ));

            gameService.saveGame(new Game(
                    null,
                    "Pixel Odyssey",
                    "Indie Platformer",
                    "Nintendo Switch",
                    4.6,
                    "A beautiful pixel-art journey with puzzles, boss fights, hidden paths, and emotional storytelling.",
                    "https://images.unsplash.com/photo-1534423861386-85a16f5d13fd?auto=format&fit=crop&w=900&q=80",
                    "https://www.youtube.com/embed/dQw4w9WgXcQ"
            ));

            System.out.println("Sample games inserted successfully.");
        }
    }
}