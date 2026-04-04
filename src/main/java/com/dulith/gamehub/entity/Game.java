package com.dulith.gamehub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String genre;
    private String platform;
    private Double rating;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String imageUrl;
    private String trailerUrl;

    @Column(nullable = false)
    private Double price = 0.0;

    private Integer discountPercent = 0;

    private Double discountedPrice = 0.0;

    @Transient
    private boolean favorite;

    public Game() {
    }

    public Game(String title, String genre, String platform, Double rating,
                String description, String imageUrl, String trailerUrl,
                Double price, Integer discountPercent, Double discountedPrice) {
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.rating = rating;
        this.description = description;
        this.imageUrl = imageUrl;
        this.trailerUrl = trailerUrl;
        this.price = price;
        this.discountPercent = discountPercent;
        this.discountedPrice = discountedPrice;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public Double getRating() {
        return rating;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTrailerUrl() {
        return trailerUrl;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getDiscountPercent() {
        return discountPercent;
    }

    public Double getDiscountedPrice() {
        if (discountedPrice != null && discountedPrice > 0) {
            return discountedPrice;
        }

        if (price != null && discountPercent != null && discountPercent > 0) {
            return price - (price * discountPercent / 100.0);
        }

        return price != null ? price : 0.0;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTrailerUrl(String trailerUrl) {
        this.trailerUrl = trailerUrl;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setDiscountPercent(Integer discountPercent) {
        this.discountPercent = discountPercent;
    }

    public void setDiscountedPrice(Double discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}