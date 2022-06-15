package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.Tanah;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TanahService {
    @GET("tanah/{node}/{spinner}")
    Call<ArrayList<Tanah>> getNodeSensor(@Path("node") String node, @Path("spinner") String spinnerMenu, @Query("sort") String sort);
}
