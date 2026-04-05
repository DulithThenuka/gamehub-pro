package com.dulith.gamehub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dulith.gamehub.entity.News;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}