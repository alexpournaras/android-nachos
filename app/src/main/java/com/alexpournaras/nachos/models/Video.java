package com.alexpournaras.nachos.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Video implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("key")
    private String youtubeId;

    @SerializedName("name")
    private String title;

    public Video(String id, String youtubeId, String title) {
        this.id = id;
        this.youtubeId = youtubeId;
        this.title = title;
    }

    public String getId() {
        return id;
    }
    public String getYoutubeId() {
        return youtubeId;
    }
    public String getTitle() {
        return title;
    }
    
}
