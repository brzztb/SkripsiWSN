package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.Tanah;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TanahService {
    @GET("tanah")
    Call<ArrayList<Tanah>> getNodeSensor();
}
