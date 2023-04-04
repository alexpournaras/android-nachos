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

    @SerializedName("site")
    private String site;

    @SerializedName("type")
    private String type;

    public Video(String id, String youtubeId, String title, String site, String type) {
        this.id = id;
        this.youtubeId = youtubeId;
        this.title = title;
        this.site = site;
        this.type = type;
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
    public String getSite() {
        return site;
    }
    public String getType() {
        return type;
    }

}
