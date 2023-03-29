package com.alexpournaras.nachos;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Movie implements Serializable {
    private String title;
    private double rating;

    @SerializedName("poster_path")
    private String posterPath;

    public Movie(String title, double rating, String posterPath) {
        this.title = title;
        this.rating = rating;
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public double getRating() {
        return rating;
    }

    public String getPosterPath() {
        return posterPath;
    }

}
