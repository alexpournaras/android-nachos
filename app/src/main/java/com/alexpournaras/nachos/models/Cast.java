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

    public Cast(int id, String name, String role, String imageUrl) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.imageUrl = imageUrl;
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
    
}
