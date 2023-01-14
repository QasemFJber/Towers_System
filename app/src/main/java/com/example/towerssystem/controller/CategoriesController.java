package com.example.towerssystem.controller;

import android.util.Log;

import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.BaseResponse;
import com.example.towerssystem.models.Categorie;
import com.example.towerssystem.towers.towrescontroller.ApiController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesController {

    public void getAllCategories(ContentCallBack<Categorie> callBack){
        Call<BaseResponse<Categorie>> call = ApiController.getInstance().getRetrofitRequests().getAllCategories();
        call.enqueue(new Callback<BaseResponse<Categorie>>() {
            @Override
            public void onResponse(Call<BaseResponse<Categorie>> call, Response<BaseResponse<Categorie>> response) {
                if (response.isSuccessful() && response.body() != null){
                    Log.v("QASEM_LIST","LIST"+response.body().list.size());
                    Log.v("QASEM_LIST","LIST"+response.body().list.size());

                    callBack.onSuccess(response.body().list);
                }else {
                    try {
                        String error = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(error);
                        callBack.onFailure(jsonObject.getString("message"));
                    }catch (JSONException | IOException jsonException){

                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Categorie>> call, Throwable t) {

            }
        });

    }
}
