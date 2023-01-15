package com.example.towerssystem.controller;

import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.Advertisements;
import com.example.towerssystem.models.BaseResponse;
import com.example.towerssystem.models.Employee;
import com.example.towerssystem.towers.towrescontroller.ApiController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertisementsController {

    public void getAllAdvertisements(ContentCallBack<Advertisements> callBack){
        Call<BaseResponse<Advertisements>> getAllAdvertisements = ApiController.getInstance().getRetrofitRequests().getAllAdvertisements();
        getAllAdvertisements.enqueue(new Callback<BaseResponse<Advertisements>>() {
            @Override
            public void onResponse(Call<BaseResponse<Advertisements>> call, Response<BaseResponse<Advertisements>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.onSuccess(response.body().list);
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Advertisements>> call, Throwable t) {
                callBack.onFailure("");

            }
        });

    }

    public void insertAdvertisements(Advertisements advertisements, AuthCallBack callBack){
        Call<BaseResponse<Advertisements>> insertAdvertisements = ApiController.getInstance().getRetrofitRequests().insertAdvertisements(advertisements.title, advertisements.info, advertisements.imageUrl);
        insertAdvertisements.enqueue(new Callback<BaseResponse<Advertisements>>() {
            @Override
            public void onResponse(Call<BaseResponse<Advertisements>> call, Response<BaseResponse<Advertisements>> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess("");
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Advertisements>> call, Throwable t) {

            }
        });

    }
    public void updateAdvertisements(int id, AuthCallBack callBack){
        Call<BaseResponse<Advertisements>> updateAdvertisements = ApiController.getInstance().getRetrofitRequests().updateAdvertisements(id);
        updateAdvertisements.enqueue(new Callback<BaseResponse<Advertisements>>() {
            @Override
            public void onResponse(Call<BaseResponse<Advertisements>> call, Response<BaseResponse<Advertisements>> response) {

                if (response.isSuccessful()) {
                    callBack.onSuccess("");
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Advertisements>> call, Throwable t) {

            }
        });

    }
    public void deleteAdvertisements(int id, AuthCallBack callBack){
        Call<BaseResponse<Advertisements>> deleteAdvertisements = ApiController.getInstance().getRetrofitRequests().deleteAdvertisements(id);
        deleteAdvertisements.enqueue(new Callback<BaseResponse<Advertisements>>() {
            @Override
            public void onResponse(Call<BaseResponse<Advertisements>> call, Response<BaseResponse<Advertisements>> response) {

                if (response.isSuccessful()) {
                    callBack.onSuccess("");
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Advertisements>> call, Throwable t) {

            }
        });

    }
}
