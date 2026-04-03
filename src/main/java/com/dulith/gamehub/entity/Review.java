package com.dulith.gamehub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;

    @Column(length = 1000)
    private String comment;

    @ManyToOne
    private User user;

    @ManyToOne
    private Game game;

    public Review() {}

    public Review(Long id, int rating, String comment, User user, Game game) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.user = user;
        this.game = game;
    }

    public Long getId() { return id; }
    public int getRating() { return rating; }
    public String getComment() { return comment; }
    public User getUser() { return user; }
    public Game getGame() { return game; }

    public void setId(Long id) { this.id = id; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }
    public void setUser(User user) { this.user = user; }
    public void setGame(Game game) { this.game = game; }
}