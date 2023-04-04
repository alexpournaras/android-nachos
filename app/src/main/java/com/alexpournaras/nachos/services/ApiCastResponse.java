package com.alexpournaras.nachos.services;

import com.alexpournaras.nachos.models.Cast;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ApiCastResponse {
    @SerializedName("cast")
    private List<Cast> results;

    public List<Cast> getResults() {
        return results;
    }

    public void setResults(List<Cast> results) {
        this.results = results;
    }
}
