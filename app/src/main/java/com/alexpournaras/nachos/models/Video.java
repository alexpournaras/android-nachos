package com.alexpournaras.nachos.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Video implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("key")
    private String youtubeId;

    public Video(String id, String youtubeId) {
        this.id = id;
        this.youtubeId = youtubeId;
    }

    public String getId() {
        return id;
    }
    public String getYoutubeId() {
        return youtubeId;
    }
    
}
