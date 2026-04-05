package com.dulith.gamehub.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.dulith.gamehub.entity.News;
import com.dulith.gamehub.repository.NewsRepository;

@Service
public class NewsService {

    private final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<News> getAllNews() {
        return newsRepository.findAll()
                .stream()
                .sorted((a, b) -> {
                    if (a.getPublishedDate() == null && b.getPublishedDate() == null) {
                        return Long.compare(
                                b.getId() != null ? b.getId() : 0L,
                                a.getId() != null ? a.getId() : 0L
                        );
                    }
                    if (a.getPublishedDate() == null) {
                        return 1;
                    }
                    if (b.getPublishedDate() == null) {
                        return -1;
                    }
                    return b.getPublishedDate().compareTo(a.getPublishedDate());
                })
                .collect(Collectors.toList());
    }

    public List<News> getLatestNews() {
        return getAllNews()
                .stream()
                .limit(6)
                .collect(Collectors.toList());
    }

    public List<News> getFeaturedNews() {
        return newsRepository.findAll()
                .stream()
                .filter(item -> item.getFeatured() != null && item.getFeatured())
                .sorted((a, b) -> {
                    if (a.getPublishedDate() == null && b.getPublishedDate() == null) {
                        return Long.compare(
                                b.getId() != null ? b.getId() : 0L,
                                a.getId() != null ? a.getId() : 0L
                        );
                    }
                    if (a.getPublishedDate() == null) {
                        return 1;
                    }
                    if (b.getPublishedDate() == null) {
                        return -1;
                    }
                    return b.getPublishedDate().compareTo(a.getPublishedDate());
                })
                .limit(3)
                .collect(Collectors.toList());
    }

    public News getNewsById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public void saveNews(News news) {
        newsRepository.save(news);
    }

    public void deleteNewsById(Long id) {
        newsRepository.deleteById(id);
    }

    public long countNews() {
        return newsRepository.count();
    }

    public List<News> getNewsByCategory(String category) {
        if (category == null || category.isBlank()) {
            return getAllNews();
        }

        return newsRepository.findAll()
                .stream()
                .filter(item -> item.getCategory() != null
                        && item.getCategory().equalsIgnoreCase(category.trim()))
                .sorted(Comparator.comparing(
                        News::getPublishedDate,
                        Comparator.nullsLast(Comparator.reverseOrder())
                ))
                .collect(Collectors.toList());
    }

    public List<String> getAllCategories() {
        return newsRepository.findAll()
                .stream()
                .map(News::getCategory)
                .filter(category -> category != null && !category.isBlank())
                .distinct()
                .collect(Collectors.toList());
    }
}