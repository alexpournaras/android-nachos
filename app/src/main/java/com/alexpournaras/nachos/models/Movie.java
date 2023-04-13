package com.alexpournaras.nachos.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Movie implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String description;

    @SerializedName("vote_average")
    private double rating;

    @SerializedName("vote_count")
    private int votes;

    @SerializedName("poster_path")
    private String posterImageUrl;

    @SerializedName("backdrop_path")
    private String backgroundImageUrl;

    public Movie(int id, String title, String releaseDate, String description, double rating, int votes, String posterImageUrl, String backgroundImageUrl) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.description = description;
        this.rating = rating;
        this.votes = votes;
        this.posterImageUrl = posterImageUrl;
        this.backgroundImageUrl = backgroundImageUrl;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public String getDescription() {
        return description;
    }
    public double getRating() {
        return rating;
    }
    public int getVotes() {
        return votes;
    }
    public String getPosterImageUrl() {
        return posterImageUrl;
    }
    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }

}
