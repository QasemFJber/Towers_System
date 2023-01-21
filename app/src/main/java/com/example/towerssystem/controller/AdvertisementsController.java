package com.example.towerssystem.controller;

import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.Advertisements;
import com.example.towerssystem.models.BaseResponse;
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

public class AdvertisementsController {

    Advertisements advertisements = new Advertisements();

    public void getAllAdvertisements(ContentCallBack<Advertisements> callBack){
        Call<BaseResponse<Advertisements>> getAllAdvertisements = ApiController.getInstance().getRetrofitRequests().getAllAdvertisements();
        getAllAdvertisements.enqueue(new Callback<BaseResponse<Advertisements>>() {
            @Override
            public void onResponse(Call<BaseResponse<Advertisements>> call, Response<BaseResponse<Advertisements>> response) {
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
            public void onFailure(Call<BaseResponse<Advertisements>> call, Throwable t) {
                callBack.onFailure("");

            }
        });

    }

    public void insertAdvertisements(String title, String info, byte[] bytes, AuthCallBack callBack){
        advertisements.imageBytesArray=bytes;
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), bytes);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image", "image-file", requestBody);
        RequestBody _title = RequestBody.create(MediaType.parse("String"),title);
        RequestBody _info = RequestBody.create(MediaType.parse("String"),info);
        Call<BaseResponse> insertAdvertisements = ApiController.getInstance().getRetrofitRequests().insertAdvertisements(_title,_info,file);
        insertAdvertisements.enqueue(new Callback<BaseResponse>() {
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
    public void updateAdvertisements(int id,String method, String title, String info, byte[] bytes, AuthCallBack callBack){
        advertisements.imageBytesArray = bytes;
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), bytes);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image", "image-file", requestBody);
        RequestBody _title = RequestBody.create(MediaType.parse("String"),title);
        RequestBody _info = RequestBody.create(MediaType.parse("String"),info);
        RequestBody _method = RequestBody.create(MediaType.parse("String"),method);
        Call<BaseResponse> updateAdvertisements = ApiController.getInstance().getRetrofitRequests().updateAdvertisements(id,_method,_title,_info,file);
        updateAdvertisements.enqueue(new Callback<BaseResponse>() {
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
    public void deleteAdvertisements(int id, AuthCallBack callBack){
        Call<BaseResponse> deleteAdvertisements = ApiController.getInstance().getRetrofitRequests().deleteAdvertisements(id);
        deleteAdvertisements.enqueue(new Callback<BaseResponse>() {
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
