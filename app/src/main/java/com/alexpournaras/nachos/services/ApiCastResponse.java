package com.alexpournaras.nachos.services;

import com.alexpournaras.nachos.models.Cast;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ApiCastResponse {
    @SerializedName("cast")
    private List<Cast> results;

    public List<Cast> getResults() {
        return results.stream()
            .filter(cast -> cast.getRole().equals("Acting"))
            .collect(Collectors.toList());
    }

    public void setResults(List<Cast> results) {
        this.results = results;
    }
}
