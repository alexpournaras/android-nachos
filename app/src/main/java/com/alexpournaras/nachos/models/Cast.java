package com.alexpournaras.nachos.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cast implements Serializable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("known_for_department")
    private String role;

    @SerializedName("profile_path")
    private String imageUrl;

    @SerializedName("popularity")
    private float popularity;

    @SerializedName("character")
    private String character;

    public Cast(int id, String name, String role, String imageUrl, float popularity, String character) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.imageUrl = imageUrl;
        this.popularity = popularity;
        this.character = character;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getRole() {
        return role;
    }
    public String getImageUrl() { return imageUrl; }
    public float getPopularity() { return popularity; }
    public String getCharacter() { return character; }
}
