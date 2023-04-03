package com.alexpournaras.nachos.services;

import com.alexpournaras.nachos.models.Video;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class ApiVideoResponse {
    @SerializedName("results")
    private List<Video> results;

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
