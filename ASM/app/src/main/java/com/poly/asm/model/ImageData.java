package com.poly.asm.model;

import com.google.gson.annotations.SerializedName;

public class ImageData {
    @SerializedName("date")
    private String date;

    @SerializedName("title")
    private String title;

    @SerializedName("explanation")
    private String explanation;

    @SerializedName("url")
    private String url;

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getUrl() {
        return url;
    }
}
