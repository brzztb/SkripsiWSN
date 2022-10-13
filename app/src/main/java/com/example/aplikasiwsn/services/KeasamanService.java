package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.SenseKeasaman;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface KeasamanService {
    @GET("phgraph")
    Call<ArrayList<SenseKeasaman>> getKeasaman();
}
