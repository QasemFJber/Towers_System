package com.example.towerssystem.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Advertisements {
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("info")
    @Expose
    public String info;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("tower_name")
    @Expose
    public String towerName;

    public byte[] imageBytesArray;

    public Advertisements() {
    }
}
