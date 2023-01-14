package com.example.towerssystem.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Admin {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("tower_name")
    @Expose
    public String towerName;
    @SerializedName("token")
    @Expose
    public String token;
    @SerializedName("token_type")
    @Expose
    public String tokenType;
    @SerializedName("refresh_token")
    @Expose
    public String refreshToken;

    public String password;

}