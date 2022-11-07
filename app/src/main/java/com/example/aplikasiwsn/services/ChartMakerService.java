package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.SenseKeasaman;
import com.example.aplikasiwsn.models.SenseKelembabanTanah;
import com.example.aplikasiwsn.models.SenseKelembabanUdara;
import com.example.aplikasiwsn.models.SenseSuhuTanah;
import com.example.aplikasiwsn.models.SenseSuhuUdara;
import com.example.aplikasiwsn.models.Tanah;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ChartMakerService {
    @GET("makechart")
    Call<ArrayList<SenseKeasaman>> getKeasamanChartData(@Query("spinner") String spinnerMenu);

    @GET("makechart")
    Call<ArrayList<SenseSuhuTanah>> getSuhuTanahChartData(@Query("spinner") String spinnerMenu);

    @GET("makechart")
    Call<ArrayList<SenseKelembabanTanah>> getKelembabanTanahChartData(@Query("spinner") String spinnerMenu);

    @GET("makechart")
    Call<ArrayList<SenseSuhuUdara>> getSuhuUdaraChartData(@Query("spinner") String spinnerMenu);

    @GET("makechart")
    Call<ArrayList<SenseKelembabanUdara>> getKelembabanUdaraChartData(@Query("spinner") String spinnerMenu);
}
