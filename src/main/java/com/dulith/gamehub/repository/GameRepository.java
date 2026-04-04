package com.dulith.gamehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dulith.gamehub.entity.Game;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByTitleContainingIgnoreCase(String title);

    List<Game> findByGenreContainingIgnoreCase(String genre);

    List<Game> findByPlatformContainingIgnoreCase(String platform);

    List<Game> findByTitleContainingIgnoreCaseAndGenreContainingIgnoreCaseAndPlatformContainingIgnoreCase(
            String title, String genre, String platform
    );
}