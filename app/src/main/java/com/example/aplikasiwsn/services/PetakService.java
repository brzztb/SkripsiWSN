package com.example.aplikasiwsn.services;

import com.example.aplikasiwsn.models.Petak;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PetakService {
    @GET("petak")
    Call<ArrayList<Petak>> getPetak();
}
