package com.example.towerssystem.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Resident {

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("mobile")
    @Expose
    public String mobile;
    @SerializedName("national_number")
    @Expose
    public String nationalNumber;
    @SerializedName("family_members")
    @Expose
    public String familyMembers;
    @SerializedName("gender")
    @Expose
    public String gender;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("tower_name")
    @Expose
    public String towerName;
    @SerializedName("token")
    @Expose
    public String token;

    public byte[] imageBytesArray;

    public Resident() {
    }
}
