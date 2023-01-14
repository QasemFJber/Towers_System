package com.example.towerssystem.towers;

import com.example.towerssystem.prefs.AppSharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSettings {
    private static Retrofit retrofit;
    private static final  String BaseURL ="https://towers.mr-dev.tech/api/";

    private RetrofitSettings(){}

    public static synchronized Retrofit getRetrofit() {
        OkHttpClient client = getClint();
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BaseURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(getClint())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getClint() {
        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        builder.addHeader("Accept","application/json");
                        builder.addHeader("Content-Type","application/json");
                        builder.addHeader("Authorization","Bearer "+ AppSharedPreferences.getInstance().getToken());
                        return chain.proceed(builder.build());
                    }
                }).build();
        return client;
    }
}
