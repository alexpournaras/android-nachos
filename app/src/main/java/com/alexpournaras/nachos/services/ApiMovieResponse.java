package com.alexpournaras.nachos.services;

import com.alexpournaras.nachos.models.Movie;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiMovieResponse {
    @SerializedName("results")
    private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
