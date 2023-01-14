package com.example.towerssystem.controller;

import android.util.Log;

import com.example.towerssystem.interfaces.AuthCallBack;
import com.example.towerssystem.interfaces.ContentCallBack;
import com.example.towerssystem.models.BaseResponse;
import com.example.towerssystem.models.Employee;
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

public class EmployeeController {
    public byte[] imageBytesArray;



    public void getAllEmployees(ContentCallBack<Employee> callBack){
        Call<BaseResponse<Employee>> getAllEmployee = ApiController.getInstance().getRetrofitRequests().getAllEmployee();
        getAllEmployee.enqueue(new Callback<BaseResponse<Employee>>() {
            @Override
            public void onResponse(Call<BaseResponse<Employee>> call, Response<BaseResponse<Employee>> response) {
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
            public void onFailure(Call<BaseResponse<Employee>> call, Throwable t) {
                callBack.onFailure("");

            }
        });

    }

    public void insertEmployee(AuthCallBack callBack){
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), imageBytesArray);
        MultipartBody.Part file = MultipartBody.Part.createFormData("image", "image-file", requestBody);
        Employee employee = new Employee();
        HashMap<String , RequestBody> bodyHashMap = new HashMap<>();
        bodyHashMap.put("name",RequestBody.create(MediaType.parse("text/plain"),employee.name));
        bodyHashMap.put("mobile",RequestBody.create(MediaType.parse("text/plain"),employee.mobile));
        bodyHashMap.put("nationalNumber",RequestBody.create(MediaType.parse("text/plain"),employee.nationalNumber));
        bodyHashMap.put("towerName",RequestBody.create(MediaType.parse("text/plain"),employee.towerName));
        Call<BaseResponse<Employee>> insertEmployee = ApiController.getInstance().getRetrofitRequests().insertEmployee(bodyHashMap,file);
        insertEmployee.enqueue(new Callback<BaseResponse<Employee>>() {
            @Override
            public void onResponse(Call<BaseResponse<Employee>> call, Response<BaseResponse<Employee>> response) {

                if (response.isSuccessful()) {
                    callBack.onSuccess("");
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Employee>> call, Throwable t) {

            }
        });

    }
    public void updateEmployee(int id, AuthCallBack callBack){
        Call<BaseResponse<Employee>> updateEmployee = ApiController.getInstance().getRetrofitRequests().updateEmployee(id);
        updateEmployee.enqueue(new Callback<BaseResponse<Employee>>() {
            @Override
            public void onResponse(Call<BaseResponse<Employee>> call, Response<BaseResponse<Employee>> response) {

                if (response.isSuccessful()) {
                    callBack.onSuccess("");
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Employee>> call, Throwable t) {

            }
        });

    }
    public void deleteEmployee(int id, AuthCallBack callBack){
        Call<BaseResponse<Employee>> deleteEmployee = ApiController.getInstance().getRetrofitRequests().deleteEmployee(id);
        deleteEmployee.enqueue(new Callback<BaseResponse<Employee>>() {
            @Override
            public void onResponse(Call<BaseResponse<Employee>> call, Response<BaseResponse<Employee>> response) {

                if (response.isSuccessful()) {
                    callBack.onSuccess("");
                } else {
                    callBack.onFailure("");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<Employee>> call, Throwable t) {

            }
        });

    }
}
