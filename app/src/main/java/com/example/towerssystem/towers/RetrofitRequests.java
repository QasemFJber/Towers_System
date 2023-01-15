package com.example.towerssystem.towers;

import com.example.towerssystem.models.Admin;
import com.example.towerssystem.models.Advertisements;
import com.example.towerssystem.models.BaseResponse;
import com.example.towerssystem.models.Categorie;
import com.example.towerssystem.models.Employee;
import com.example.towerssystem.models.Operations;
import com.example.towerssystem.models.Resident;

import java.util.HashMap;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

//Service Interface - Retrofit - API EndPoints
public interface RetrofitRequests {


    /***
     * Auth Requests
     *
     * LOGIN
     * LOGOUT
     * CHANGE PASSWORD
     */

    @FormUrlEncoded
    @POST("auth/login")
    Call<BaseResponse<Admin>> login (@Field("email") String email , @Field("password") String password );

    @GET("auth/logout")
    Call<BaseResponse> logout();

    @FormUrlEncoded
    @POST("auth/change-password")
    Call<BaseResponse> changePassword(@Field("current_password") String current_password,@Field("new_password") String new_password,@Field("new_password_confirmation") String new_password_confirmation);

    @GET("categories")
    Call<BaseResponse<Categorie>> getAllCategories();

    /***
     * EMPLOYEE Requests
     *
     * INSERT
     * UPDATE
     * DELETE
     * GET
     */


    @GET("employees")
    Call<BaseResponse<Employee>> getAllEmployee();

    @Multipart
    @POST("employees")
    Call<BaseResponse> insertEmployee(@Part("name") RequestBody name,
                                      @Part("mobile") RequestBody mobile,
                                      @Part("national_number") RequestBody national_number,
                                      @Part MultipartBody.Part image);

    @PUT("employees/{id}")
    Call<BaseResponse<Employee>> updateEmployee(@Path("id") int id);

    @DELETE("employees/{id}")
    Call<BaseResponse<Employee>> deleteEmployee(@Path("id") int id);





    /***
     * USERS Requests
     *
     * INSERT
     * UPDATE
     * DELETE
     * GET
     *
     */


    @GET("users")
    Call<BaseResponse<Resident>> getAllResident();

    @Multipart
    @POST("users")
    Call<BaseResponse> insertResident(@Part("name") RequestBody name
            ,@Part("email") RequestBody email,
                                      @Part("mobile") RequestBody mobile,
                                      @Part("national_number") RequestBody national_number,
                                      @Part("family_members") RequestBody family_members,
                                      @Part("gender") RequestBody gender,
                                      @Part MultipartBody.Part image);


    @PUT("users/{id}")
    Call<BaseResponse<Resident>> updateResident(@Path("id") int id);

    @DELETE("users/{id}")
    Call<BaseResponse<Resident>> deleteResident(@Path("id") int id);





    /***
     * Operations Requests
     *
     * INSERT
     * UPDATE
     * DELETE
     * GET
     *
     */

    @GET("operations")
    Call<BaseResponse<Operations>> getAllOperations();

    @FormUrlEncoded
    @POST("operations")
    Call<BaseResponse<Operations>> insertOperations(@Field("category_id") String category_id,@Field("amount") String amount,@Field("details") String details ,@Field("actor_id") String actor_id,@Field("actor_type") String actor_type,@Field("date") String date);

    @PUT("operations/{id}")
    Call<BaseResponse<Operations>> updateOperations(@Path("id") int id);

    @DELETE("operations/{id}")
    Call<BaseResponse<Operations>> deleteOperations(@Path("id") int id);




    /***
     * Advertisements Requests
     *
     * INSERT
     * UPDATE
     * DELETE
     * GET
     *
     */





    @GET("advertisements")
    Call<BaseResponse<Advertisements>> getAllAdvertisements();

    @FormUrlEncoded
    @POST("advertisements")
    Call<BaseResponse<Advertisements>> insertAdvertisements(@Field("title") String title, @Field("info") String info, @Field("image") String image);

    @PUT("advertisements/{id}")
    Call<BaseResponse<Advertisements>> updateAdvertisements(@Path("id") int id);

    @DELETE("advertisements/{id}")
    Call<BaseResponse<Advertisements>> deleteAdvertisements(@Path("id") int id);




}
