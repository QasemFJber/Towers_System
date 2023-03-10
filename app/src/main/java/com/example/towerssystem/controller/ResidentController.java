package com.example.towerssystem.controller;

import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.BaseResponse;
import com.example.towerssystem.models.Resident;
import com.example.towerssystem.towers.towrescontroller.ApiController;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResidentController {


    Resident resident = new Resident();

    public void getAllResident(ContentCallBack<Resident> callBack){
        Call<BaseResponse<Resident>> getAllResident = ApiController.getInstance().getRetrofitRequests().getAllResident();
        getAllResident.enqueue(new Callback<BaseResponse<Resident>>() {
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

    public void insertResident(String name,String email,String mobile,String nationalNumber , String familyMembers,String gender,byte[] bytes,AuthCallBack callBack){
        resident.imageBytesArray = bytes;
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), bytes);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image", "image-file", requestBody);
        RequestBody _name = RequestBody.create(MediaType.parse("String"),name);
        RequestBody _email = RequestBody.create(MediaType.parse("String"),email);
        RequestBody _mobile = RequestBody.create(MediaType.parse("String"),mobile);
        RequestBody _nationalNumber = RequestBody.create(MediaType.parse("String"),nationalNumber);
        RequestBody _familyMembers = RequestBody.create(MediaType.parse("String"),familyMembers);
        RequestBody _gender = RequestBody.create(MediaType.parse("String"),gender);
        Call<BaseResponse> insertResident = ApiController.getInstance().getRetrofitRequests().insertResident(_name,_email,_mobile,_nationalNumber,_familyMembers,_gender,file);
       insertResident.enqueue(new Callback<BaseResponse>() {
           @Override
           public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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
           public void onFailure(Call<BaseResponse> call, Throwable t) {
               callBack.onFailure("Something Wont Wrong");

           }
       });

    }
    public void updateResident(int id,String method ,String name,String email,String mobile,String nationalNumber , String familyMembers,String gender,byte[] bytes, AuthCallBack callBack){
        resident.imageBytesArray=bytes;
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), bytes);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image", "image-file", requestBody);
        RequestBody _method = RequestBody.create(MediaType.parse("String"),method);
        RequestBody _name = RequestBody.create(MediaType.parse("String"),name);
        RequestBody _email = RequestBody.create(MediaType.parse("String"),email);
        RequestBody _mobile = RequestBody.create(MediaType.parse("String"),mobile);
        RequestBody _nationalNumber = RequestBody.create(MediaType.parse("String"),nationalNumber);
        RequestBody _familyMembers = RequestBody.create(MediaType.parse("String"),familyMembers);
        RequestBody _gender = RequestBody.create(MediaType.parse("String"),gender);
        Call<BaseResponse> updateUser = ApiController.getInstance().getRetrofitRequests().updateResident(id,_method,_name,_email,_mobile,_nationalNumber,_familyMembers,_gender,file);
        updateUser.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
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
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                callBack.onFailure("Something wont Wrong");

            }
        });
    }
    public void deleteResident(int id , AuthCallBack callBack){
        Call<BaseResponse> deleteUser = ApiController.getInstance().getRetrofitRequests().deleteResident(id);
        deleteUser.enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.isSuccessful() && response.body() !=null) {
                    callBack.onSuccess(response.body().message);
                } else {
                    try {
                        String error = new String(response.errorBody().bytes(), StandardCharsets.UTF_8);
                        JSONObject jsonObject = new JSONObject(error);
                        callBack.onFailure(jsonObject.getString("message"));
                    }catch (JSONException jsonException){

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {

            }
        });

    }
}