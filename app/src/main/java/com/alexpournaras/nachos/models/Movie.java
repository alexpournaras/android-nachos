package com.alexpournaras.nachos.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Movie implements Serializable {
    @SerializedName("title")
    private String title;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("overview")
    private String description;

    @SerializedName("vote_average")
    private double rating;

    @SerializedName("vote_count")
    private double votes;

    @SerializedName("poster_path")
    private String posterImageUrl;

    @SerializedName("backdrop_path")
    private String backgroundImageUrl;

    public Movie(String title, String releaseDate, String description, double rating, double votes, String posterImageUrl, String backgroundImageUrl) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.description = description;
        this.rating = rating;
        this.votes = votes;
        this.posterImageUrl = posterImageUrl;
        this.backgroundImageUrl = backgroundImageUrl;
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
    public double getVotes() {
        return votes;
    }
    public String getPosterImageUrl() {
        return posterImageUrl;
    }
    public String getBackgroundImageUrl() {
        return backgroundImageUrl;
    }


}
