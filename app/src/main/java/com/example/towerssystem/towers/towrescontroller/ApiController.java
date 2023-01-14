package com.example.towerssystem.towers.towrescontroller;


import com.example.towerssystem.towers.RetrofitRequests;
import com.example.towerssystem.towers.RetrofitSettings;

public class ApiController {

    private RetrofitRequests retrofitRequests;
    private static ApiController instance;

    private ApiController() {
        retrofitRequests = RetrofitSettings.getRetrofit().create(RetrofitRequests.class);
    }

    public static synchronized ApiController getInstance() {
        if(instance == null) {
            instance = new ApiController();
        }
        return instance;
    }

    public RetrofitRequests getRetrofitRequests() {
        return retrofitRequests;
    }
}
