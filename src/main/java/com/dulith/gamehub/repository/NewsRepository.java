package com.dulith.gamehub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dulith.gamehub.entity.News;

public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByOrderByPublishedDateDesc();

    Optional<News> findFirstByFeaturedTrueOrderByPublishedDateDesc();

    Optional<News> findTopByOrderByPublishedDateDesc();

    List<News> findTop6ByOrderByPublishedDateDesc();
}