package com.example.towerssystem.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BaseResponse<Model> {

    @SerializedName("status")
    @Expose
    public Boolean status;
//    @SerializedName("type")
//    @Expose
//    public String type;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("list")
    @Expose
    public List<Model> list = null;
    @SerializedName("data")
    @Expose
    public Model data;


}