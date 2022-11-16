package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.SenseKeasaman;
import com.example.aplikasiwsn.models.SenseKelembabanTanah;
import com.example.aplikasiwsn.models.SenseKelembabanUdara;
import com.example.aplikasiwsn.models.SenseSuhuTanah;
import com.example.aplikasiwsn.models.SenseSuhuUdara;
import com.example.aplikasiwsn.models.Tanah;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ChartMakerService {
    @GET("makechart")
    Call<ArrayList<SenseKeasaman>> getKeasamanChartData(@Query("spinner") String spinnerMenu, @QueryMap HashMap<String, String> since);

    @GET("makechart")
    Call<ArrayList<SenseSuhuTanah>> getSuhuTanahChartData(@Query("spinner") String spinnerMenu, @QueryMap HashMap<String, String> since);

    @GET("makechart")
    Call<ArrayList<SenseKelembabanTanah>> getKelembabanTanahChartData(@Query("spinner") String spinnerMenu, @QueryMap HashMap<String, String> since);

    @GET("makechart")
    Call<ArrayList<SenseSuhuUdara>> getSuhuUdaraChartData(@Query("spinner") String spinnerMenu, @QueryMap HashMap<String, String> since);

    @GET("makechart")
    Call<ArrayList<SenseKelembabanUdara>> getKelembabanUdaraChartData(@Query("spinner") String spinnerMenu, @QueryMap HashMap<String, String> since);
}
