package com.dulith.gamehub.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name = "news")
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 1200)
    private String summary;

    @Lob
    private String content;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private String category;

    @Column(name = "read_time")
    private String readTime;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    @Column(nullable = false)
    private boolean featured;

    public News() {
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getSummary() {
        return summary;
    }

    public String getContent() {
        return content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public String getReadTime() {
        return readTime;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public boolean isFeatured() {
        return featured;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setFeatured(boolean featured) {
        this.featured = featured;
    }
}