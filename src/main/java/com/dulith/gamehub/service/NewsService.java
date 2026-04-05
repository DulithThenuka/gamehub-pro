package com.dulith.gamehub.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dulith.gamehub.entity.News;
import com.dulith.gamehub.repository.NewsRepository;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getLatestNews() {
        return newsRepository.findAllByOrderByPublishedDateDesc();
    }

    public News getFeaturedNews() {
        return newsRepository.findFirstByFeaturedTrueOrderByPublishedDateDesc()
                .orElseGet(() -> newsRepository.findTopByOrderByPublishedDateDesc().orElse(null));
    }

    public List<News> getPopularNews() {
        return newsRepository.findTop6ByOrderByPublishedDateDesc();
    }

    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }
}