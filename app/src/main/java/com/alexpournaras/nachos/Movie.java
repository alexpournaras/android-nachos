package com.alexpournaras.nachos;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private double rating;

    public Movie(String title, double rating) {
        this.title = title;
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }
}
