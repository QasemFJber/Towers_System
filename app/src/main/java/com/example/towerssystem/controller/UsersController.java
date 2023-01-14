package com.example.towerssystem.controller;

import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.BaseResponse;
import com.example.towerssystem.models.Employee;
import com.example.towerssystem.models.Resident;
import com.example.towerssystem.towers.towrescontroller.ApiController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersController {
    public byte[] imageBytesArray;

    public void getAllUsers(ContentCallBack<Resident> callBack){
        Call<BaseResponse<Resident>> getAllUsers = ApiController.getInstance().getRetrofitRequests().getAllResident();
        getAllUsers.enqueue(new Callback<BaseResponse<Resident>>() {
            @Override
            public void onResponse(Call<BaseResponse<Resident>> call, Response<BaseResponse<Resident>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.onSuccess(response.body().list);
                } else {
                    try {
                        String error = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(error);
                        callBack.onFailure(jsonObject.getString("message"));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Resident>> call, Throwable t) {
                callBack.onFailure("");

            }
        });

    }

    public void insertUser(String name,String email ,String mobile,String national_number ,String family_members ,String gender, String image, AuthCallBack callBack){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageBytesArray);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image", "image-file", requestBody);
        Resident resident = new Resident();
        Call<BaseResponse<Resident>> insertUser = ApiController.getInstance().getRetrofitRequests().insertResident();
        insertUser.enqueue(new Callback<BaseResponse<Resident>>() {
            @Override
            public void onResponse(Call<BaseResponse<Resident>> call, Response<BaseResponse<Resident>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callBack.onSuccess(response.body().message);
                } else {
                    try {
                        String error = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(error);
                        callBack.onFailure(jsonObject.getString("message"));
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<BaseResponse<Resident>> call, Throwable t) {
                callBack.onFailure("");

            }
        });

    }
    public void updateUser(int id, AuthCallBack callBack){
        Call<BaseResponse<Resident>> updateUser = ApiController.getInstance().getRetrofitRequests().updateResident(id);
        updateUser.enqueue(new Callback<BaseResponse<Resident>>() {
            @Override
            public void onResponse(Call<BaseResponse<Resident>> call, Response<BaseResponse<Resident>> response) {

                if (response.isSuccessful()) {
                    callBack.onSuccess("");
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Resident>> call, Throwable t) {

                callBack.onFailure("");
            }
        });

    }
    public void deleteUser(int id , AuthCallBack callBack){
        Call<BaseResponse<Resident>> deleteUser = ApiController.getInstance().getRetrofitRequests().deleteResident(id);
        deleteUser.enqueue(new Callback<BaseResponse<Resident>>() {
            @Override
            public void onResponse(Call<BaseResponse<Resident>> call, Response<BaseResponse<Resident>> response) {
                if (response.isSuccessful()) {
                    callBack.onSuccess("");
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Resident>> call, Throwable t) {
                callBack.onFailure("");

            }
        });

    }
}