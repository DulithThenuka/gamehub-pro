package com.dulith.gamehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dulith.gamehub.entity.Game;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
}