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

    public List<Movie> getFirst15Results() {
        if (results.size() <= 15) {
            return results;
        } else {
            return results.subList(0, 15);
        }
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
