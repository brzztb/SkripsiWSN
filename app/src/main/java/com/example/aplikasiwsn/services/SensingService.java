package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.Tanah;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface SensingService {
    @GET("sensing")
    Call<ArrayList<Tanah>> getSensing(@QueryMap HashMap<String, String> since);
}
