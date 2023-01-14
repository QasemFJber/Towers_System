package com.example.towerssystem.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Categorie {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("actions_count")
    @Expose
    public String actionsCount;
    @SerializedName("total")
    @Expose
    public String total;

}