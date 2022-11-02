package com.example.aplikasiwsn.services;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NodeCountService {
    @GET("nodecount")
    Call<String> getNodeCount();
}
