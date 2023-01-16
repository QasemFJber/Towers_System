package com.example.towerssystem.controller;

import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.Advertisements;
import com.example.towerssystem.models.BaseResponse;
import com.example.towerssystem.models.Employee;
import com.example.towerssystem.towers.towrescontroller.ApiController;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertisementsController {
    public byte[] imageBytesArray;

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
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageBytesArray);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image", "image-file", requestBody);
        RequestBody _title = RequestBody.create(MediaType.parse("String"),advertisements.title);
        RequestBody _info = RequestBody.create(MediaType.parse("String"),advertisements.info);
        Call<BaseResponse> insertAdvertisements = ApiController.getInstance().getRetrofitRequests().insertAdvertisements(_title,_info,file);
        insertAdvertisements.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });
    }
    public void updateAdvertisements(int id, AuthCallBack callBack){
        Call<BaseResponse> updateAdvertisements = ApiController.getInstance().getRetrofitRequests().updateAdvertisements(id);
        updateAdvertisements.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }
    public void deleteAdvertisements(int id, AuthCallBack callBack){
        Call<BaseResponse> deleteAdvertisements = ApiController.getInstance().getRetrofitRequests().deleteAdvertisements(id);
        deleteAdvertisements.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }
}
