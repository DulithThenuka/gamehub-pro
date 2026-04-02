package com.dulith.gamehub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dulith.gamehub.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByTitleContainingIgnoreCase(String title);

    List<Game> findByGenreIgnoreCase(String genre);

    List<Game> findByPlatformIgnoreCase(String platform);

    List<Game> findByTitleContainingIgnoreCaseAndGenreIgnoreCase(String title, String genre);

    List<Game> findByTitleContainingIgnoreCaseAndPlatformIgnoreCase(String title, String platform);

    List<Game> findByGenreIgnoreCaseAndPlatformIgnoreCase(String genre, String platform);

    List<Game> findByTitleContainingIgnoreCaseAndGenreIgnoreCaseAndPlatformIgnoreCase(String title, String genre, String platform);
}