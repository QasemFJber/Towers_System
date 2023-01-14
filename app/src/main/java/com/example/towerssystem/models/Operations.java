package com.example.towerssystem.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Operations {

    @SerializedName("category_id")
    @Expose
    public String categoryId;
    @SerializedName("amount")
    @Expose
    public String amount;
    @SerializedName("details")
    @Expose
    public String details;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("actor_id")
    @Expose
    public Integer actorId;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("category_name")
    @Expose
    public String categoryName;

}
