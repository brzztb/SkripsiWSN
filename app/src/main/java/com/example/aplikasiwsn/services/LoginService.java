package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.User;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @FormUrlEncoded
    @POST("login")
    Call<User> loginRequest(@Field("username") String username, @Field("password") String password);
}