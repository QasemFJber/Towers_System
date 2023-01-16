package com.example.towerssystem.controller;

import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.BaseResponse;
import com.example.towerssystem.models.Employee;
import com.example.towerssystem.models.Operations;
import com.example.towerssystem.towers.towrescontroller.ApiController;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OperationsController {

    public void getAllOperations(ContentCallBack<Operations> callBack){
        Call<BaseResponse<Operations>> getAllOperations = ApiController.getInstance().getRetrofitRequests().getAllOperations();
        getAllOperations.enqueue(new Callback<BaseResponse<Operations>>() {
            @Override
            public void onResponse(Call<BaseResponse<Operations>> call, Response<BaseResponse<Operations>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.onSuccess(response.body().list);
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Operations>> call, Throwable t) {
                callBack.onFailure("");

            }
        });

    }

    public void insertOperations(String category_id,String amount,String details , String actor_id,String actor_type,String date , AuthCallBack callBack){
        Call<BaseResponse> insertOperations = ApiController.getInstance().getRetrofitRequests().insertOperations(category_id,amount,details,actor_id,actor_type,date);
        insertOperations.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }
    public void updateOperations(int id, AuthCallBack callBack){
        Call<BaseResponse> updateOperations = ApiController.getInstance().getRetrofitRequests().updateOperations(id);
        updateOperations.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }
    public void deleteOperations(int id, AuthCallBack callBack){
        Call<BaseResponse> deleteOperations = ApiController.getInstance().getRetrofitRequests().deleteOperations(id);
        deleteOperations.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {

            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }
}
