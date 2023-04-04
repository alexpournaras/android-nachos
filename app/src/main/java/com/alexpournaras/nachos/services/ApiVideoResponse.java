package com.alexpournaras.nachos.services;

import com.alexpournaras.nachos.models.Video;
import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.stream.Collectors;

public class ApiVideoResponse {
    @SerializedName("results")
    private List<Video> results;

    public List<Video> getResults() {
        return results.stream()
            .filter(video -> video.getSite().equals("YouTube"))
            .filter(video -> video.getType().equals("Trailer"))
            .collect(Collectors.toList());
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
