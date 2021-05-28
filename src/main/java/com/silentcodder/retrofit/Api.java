package com.silentcodder.retrofit;

import com.silentcodder.retrofit.Model.LoginResponse;
import com.silentcodder.retrofit.Model.RegisterResponse;
import com.silentcodder.retrofit.Model.UpdateResponse;
import com.silentcodder.retrofit.Model.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("name") String username,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );


    @GET("fetchusers.php")
    Call<UserResponse> users();

    @FormUrlEncoded
    @POST("updateuser.php")
    Call<LoginResponse> update(
            @Field("id") int id,
            @Field("username") String username,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("updatepassword.php")
    Call<UpdateResponse> updatePassword(
            @Field("current") String currentPassword,
            @Field("new") String newPassword,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("deleteuser.php")
    Call<LoginResponse> delete(
            @Field("id") int id
    );

}
