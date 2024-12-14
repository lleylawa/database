package org.example.movie;

import java.sql.Date;

public class Review {
    private int id;
    private int movieId;
    private String reviewText;
    private int rating;
    private Date reviewDate;

    public Review(int id, int movieId, String reviewText, int rating, Date reviewDate) {
        this.id = id;
        this.movieId = movieId;
        this.reviewText = reviewText;
        this.rating = rating;
        this.reviewDate = reviewDate;
    }

    public int getId() {
        return id;
    }

    public int getMovieId() {
        return movieId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public int getRating() {
        return rating;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    @Override
    public String toString() {
        return "Rating: " + rating + "/5, Review: " + reviewText + " (Date: " + reviewDate + ")";
    }

}
