package com.example.towerssystem.controller;

import android.util.Log;

import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.models.Admin;
import com.example.towerssystem.models.BaseResponse;
import com.example.towerssystem.prefs.AppSharedPreferences;
import com.example.towerssystem.towers.towrescontroller.ApiController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthController {


    public void  login (String email , String password , AuthCallBack authCallBack){
        Call<BaseResponse<Admin>> call = ApiController.getInstance().getRetrofitRequests().login(email, password);
        call.enqueue(new Callback<BaseResponse<Admin>>() {
            @Override
            public void onResponse(Call<BaseResponse<Admin>> call, Response<BaseResponse<Admin>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AppSharedPreferences.getInstance().save(response.body().data);
                    authCallBack.onSuccess(response.body().message);
                } else {
                    try {
                        String error = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(error);
                        Log.e("Retrofit-API", "onResponse: " + error);
                        Log.e("Retrofit-API", "onResponse: " + jsonObject.getString("message"));
                        authCallBack.onFailure(jsonObject.getString("message"));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<BaseResponse<Admin>> call, Throwable t) {
               authCallBack.onFailure(t.getMessage());

            }
        });

    }


    public void logout (AuthCallBack authCallBack){
        Call<BaseResponse> call = ApiController.getInstance().getRetrofitRequests().logout();
        call.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.code() == 200 || response.code() == 401) {
                    AppSharedPreferences.getInstance().clear();
                    authCallBack.onSuccess(response.code() == 200 ? response.body().message : "Logged out successfully");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Log.d("onFailure","RETROFIT : fail");

            }
        });
    }


    public void changePassword(String current_password,String new_password , String new_password_confirmation , AuthCallBack authCallBack){
        Call<BaseResponse> changePassword = ApiController.getInstance().getRetrofitRequests().changePassword(current_password,new_password,new_password_confirmation);
        changePassword.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    authCallBack.onSuccess(response.body().message);
                } else {
                    try {
                        String error = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(error);
                        Log.e("Retrofit-API", "onResponse: " + error);
                        Log.e("Retrofit-API", "onResponse: " + jsonObject.getString("message"));
                        authCallBack.onFailure(jsonObject.getString("message"));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                authCallBack.onFailure("");

            }
        });

    }
}
